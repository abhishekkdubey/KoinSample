package com.example.finin.network

interface GetUserListener {
    fun onProgress()
    fun onSuccess()
    fun onError(code: Int, msg: String)
}