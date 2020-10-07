package com.example.koin.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_TIMEOUT: Long = 30
private const val BASE_URL = "https://reqres.in/api/"


fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder.readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    builder.writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    builder.connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    return builder.addInterceptor(loggingInterceptor).build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY
    return logger
}

fun provideUserApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)
