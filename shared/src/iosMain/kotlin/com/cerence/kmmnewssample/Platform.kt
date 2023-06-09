package com.cerence.kmmnewssample

import com.cerence.kmmnewssample.presentation.NewsViewModel
import io.ktor.client.engine.darwin.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        Darwin.create()
    }

    //single or factory can be used to get a view-model object for swiftui

    single {
        NewsViewModel(get())
    }
}

/**
 * ViewModels object implements koin component thus its able to get any
 * dependency that is listed in platform module we can call getNewsViewModel()
 * in swift ui to get an object of HomeViewModel
 */

object ViewModels : KoinComponent {
    fun getNewsViewModel() = get<NewsViewModel>()
}

actual interface CommonParcelable
