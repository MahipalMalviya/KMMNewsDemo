package com.cerence.kmmnewssample

import android.os.Parcelable
import com.cerence.kmmnewssample.presentation.NewsViewModel
import io.ktor.client.engine.android.*
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * shared implementation of parcelable
 */
actual typealias CommonParcelize = Parcelize

actual typealias CommonParcelable = Parcelable

actual fun platformModule() = module {
    single {
        Android.create()
    }

    /**
     *
     * for android koin has a special viewModel scope that we can use
     * to create a viewModel
     *
     */

    viewModel {
        NewsViewModel(get())
    }
}