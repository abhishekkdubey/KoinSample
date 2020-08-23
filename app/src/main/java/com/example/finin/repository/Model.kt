package com.example.finin.repository

import com.example.finin.db.User
import com.example.finin.network.GetUserListener

interface Model<T> {
    suspend fun saveToDB(users: List<T>, callback: (Boolean) -> Unit)
    suspend fun fetchFromDB(): List<T>
    suspend fun fetchFromNetwork(page: Int, listener: GetUserListener): List<User>
}