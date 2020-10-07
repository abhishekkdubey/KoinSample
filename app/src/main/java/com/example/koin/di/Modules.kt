package com.example.koin.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.koin.db.User
import com.example.koin.db.UserDatabase
import com.example.koin.network.provideLoggingInterceptor
import com.example.koin.network.provideOkHttpClient
import com.example.koin.network.provideRetrofit
import com.example.koin.network.provideUserApi
import com.example.koin.repository.IUsersRepository
import com.example.koin.repository.MainRepository
import com.example.koin.repository.Model
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module(override = true) {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "fin_pref",
            Context.MODE_PRIVATE
        )
    }
    single<Model<User>> { MainRepository }
    single<IUsersRepository> { MainRepository }
    factory { provideOkHttpClient(get()) }
    factory { provideUserApi(get()) }
    factory { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }
    single {
        Room.databaseBuilder(androidContext(), UserDatabase::class.java, "fin_db").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

}
