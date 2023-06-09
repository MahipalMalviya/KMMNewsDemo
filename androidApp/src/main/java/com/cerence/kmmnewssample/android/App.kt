package com.cerence.kmmnewssample.android

import android.app.Application
import com.cerence.kmmnewssample.domain.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(baseUrl = "https://newsapi.org/v2", enableNetworkLogs = BuildConfig.DEBUG) {
            androidContext(this@App)
            modules(
                listOf(module {  })
            )
        }
    }
}