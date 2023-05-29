package com.cerence.kmmnewssample

import com.cerence.kmmnewssample.presentation.NewsViewModel
import io.ktor.client.engine.darwin.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun platformModule() = module {
    single {
        Darwin.create()
    }

    single {
        NewsViewModel(get())
    }
}

/**
 * ViewModels object implements koin component thus its able to get any
 * dependency that is listed in platform module we can call getHomeviewmodel()
 * in swift ui to get an object of HomeViewModel
 */

object ViewModels : KoinComponent {
    fun getNewsViewModel() = get<NewsViewModel>()
}

actual interface CommonParcelable
