package com.example.koin.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.koin.db.Data
import com.example.koin.db.User
import com.example.koin.db.UserDatabase
import com.example.koin.network.GetUserListener
import com.example.koin.network.UsersApi
import com.example.koin.utility.Constants.AVAILABLE_PAGES
import com.example.koin.utility.Constants.IS_DB_UPDATED
import com.example.koin.utility.Constants.LAST_UPDATE
import com.example.koin.utility.Constants.PAGE_SYNCED
import com.example.koin.utility.NetworkUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection


interface IUsersRepository {
    suspend fun getUsers(
        listener: GetUserListener,
        loadMore: Boolean = false
    ): List<User>

    fun hasMoreUsers(): Boolean
}

object MainRepository : Model<User>, KoinComponent, IUsersRepository {

    val TAG = MainRepository::class.java.simpleName
    private val database: UserDatabase by inject()
    private val usersApi: UsersApi by inject()
    private val sharedPreferences: SharedPreferences by inject()

    private lateinit var atomicInt: CountDownLatch


    override suspend fun fetchFromDB(): List<User> {
        return coroutineScope {
            async(Dispatchers.IO) {
                return@async database.userDao().getUser()
            }.await()
        }
    }


    override suspend fun saveToDB(users: List<User>, callback: (Boolean) -> Unit) {
        callback(coroutineScope {
            async(Dispatchers.IO) {
                database.userDao().insertUserList(users)
                return@async true
            }.await()
        })
    }

    private fun setLastDBUpdated(): Boolean {
        return with(sharedPreferences.edit()) {
            return@with putLong(LAST_UPDATE, System.currentTimeMillis()).commit()
        }
    }

    private fun availablePages(): Int {
        return sharedPreferences.getInt(AVAILABLE_PAGES, 0)
    }

    private fun syncedPages(): Int {
        return sharedPreferences.getInt(PAGE_SYNCED, 0)
    }

    private fun setAvailablePages(value: Int): Boolean {
        return with(sharedPreferences.edit()) {
            return@with putInt(AVAILABLE_PAGES, value).commit()
        }

    }

    private fun setSyncedPages(value: Int): Boolean {
        return with(sharedPreferences.edit()) {
            return@with putInt(PAGE_SYNCED, value).commit()
        }
    }


    private fun getLastUpdateTime(): Long {
        return sharedPreferences.getLong(LAST_UPDATE, 0)
    }

    private fun isDBUpdated(): Boolean {
        return sharedPreferences.getBoolean(IS_DB_UPDATED, false)
    }

    private fun setDBSyncCompleted(): Boolean {
        return with(sharedPreferences.edit()) {
            return@with putBoolean(IS_DB_UPDATED, true).commit()
        }
    }

    override suspend fun getUsers(
        listener: GetUserListener,
        loadMore: Boolean
    ): List<User> {
        listener.onProgress()
        if (isDBUpdated()) {
            listener.onSuccess()
        } else {
            if (availablePages() == 0) {
                fetchFromNetwork(1, listener)
            } else if (loadMore) {
                fetchFromNetwork(syncedPages() + 1, listener)
            }
        }
        return fetchFromDB()
    }

    override fun hasMoreUsers(): Boolean {
        return if (availablePages() == 0)
            true
        else syncedPages() < availablePages()

    }


    override suspend fun fetchFromNetwork(page: Int, listener: GetUserListener): List<User> {
        var userList: List<User> = emptyList()
        if (NetworkUtility.isConnected(get())) {
            atomicInt = CountDownLatch(1)
            usersApi.getUsers(page = page).enqueue(object : Callback<Data> {
                override fun onFailure(call: Call<Data>, t: Throwable) {
                    listener.onError(call.hashCode(), t.message ?: "Something Went wrong!!")
                    atomicInt.countDown()
                }

                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    val responseCode = response.code()
                    Log.d(TAG, "onResponse: responseCode:$responseCode")
                    if (HttpsURLConnection.HTTP_OK == responseCode) {
                        val body = response.body()
                        if (null != body) {
                            if (body.users.isNotEmpty()) {
                                database.userDao().insertUserList(body.users)
                            }
                            setAvailablePages(body.totalPages)
                            setSyncedPages(body.page)
                            userList = body.users
                            setDBSyncCompleted()
                            setLastDBUpdated()
                        }
                    }
                    atomicInt.countDown()
                }

            })
            val result: Boolean = try {
                atomicInt.await(60, TimeUnit.SECONDS)
            } catch (e: Exception) {
                false
            } finally {
                atomicInt.countDown()
            }
            if (result) {
                return userList
            }

        } else {
            listener.onError(-1, "Network Error!!")
        }
        return userList
    }

}