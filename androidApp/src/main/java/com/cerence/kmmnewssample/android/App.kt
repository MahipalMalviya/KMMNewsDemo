package com.cerence.kmmnewssample.android

import android.app.Application
import com.cerence.kmmnewssample.domain.di.initKoin
import com.cerence.kmmnewssample.presentation.NewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.get
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(baseUrl = "https://newsapi.org/v2/") {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
//            modules(modules = module { single { NewsViewModel(get()) } })
        }
    }
}