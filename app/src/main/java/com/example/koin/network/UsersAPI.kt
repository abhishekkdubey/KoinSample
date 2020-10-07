package com.example.koin.network

import com.example.koin.db.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET("users")
    fun getUsers(@Query("page") page: Int = 1, @Query("delay") delay: Int = 3): Call<Data>
}
