package com.example.finin.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.finin.db.User
import com.example.finin.db.UserDatabase
import com.example.finin.network.provideLoggingInterceptor
import com.example.finin.network.provideOkHttpClient
import com.example.finin.network.provideRetrofit
import com.example.finin.network.provideUserApi
import com.example.finin.repository.IUsersRepository
import com.example.finin.repository.MainRepository
import com.example.finin.repository.Model
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
