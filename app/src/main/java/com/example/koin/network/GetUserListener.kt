package com.example.koin.network

interface GetUserListener {
    fun onProgress()
    fun onSuccess()
    fun onError(code: Int, msg: String)
}