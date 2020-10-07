package com.example.koin.repository

import com.example.koin.db.User
import com.example.koin.network.GetUserListener

interface Model<T> {
    suspend fun saveToDB(users: List<T>, callback: (Boolean) -> Unit)
    suspend fun fetchFromDB(): List<T>
    suspend fun fetchFromNetwork(page: Int, listener: GetUserListener): List<User>
}