package com.example.koin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koin.db.User
import com.example.koin.network.GetUserListener
import com.example.koin.repository.IUsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainViewModel : ViewModel(), KoinComponent {

    private val repository: IUsersRepository by inject()

    val userLiveData = MutableLiveData<List<User>>()

    fun hasMoreUsers(): Boolean{
      return  repository.hasMoreUsers()
    }

    fun getUsers(loadMore: Boolean = false): LiveData<String> {
        val status = MutableLiveData<String>()
        viewModelScope.launch(Dispatchers.Main) {
            val userList = async(Dispatchers.IO) {
                repository.getUsers(loadMore = loadMore, listener = object : GetUserListener {
                    override fun onProgress() {
                        status.postValue("IN_PROGRESS")
                    }

                    override fun onSuccess() {
                        status.postValue("ON_SUCCESS")
                    }

                    override fun onError(code: Int, msg: String) {
                        status.postValue("Error:$msg")
                    }

                })
            }
            userLiveData.postValue(userList.await())
        }
        return status
    }

}
