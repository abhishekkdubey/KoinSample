package com.example.koin

import android.app.Application
import com.example.koin.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoinSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@KoinSampleApplication)
            modules(
                listOf(
                    applicationModule
                )
            )
        }

    }
}
