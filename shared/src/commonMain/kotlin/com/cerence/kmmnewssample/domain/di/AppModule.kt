package com.cerence.kmmnewssample.domain.di

import com.cerence.kmmnewssample.data.remote.service.AbstractApiService
import com.cerence.kmmnewssample.data.remote.service.ImplNewsApiService
import com.cerence.kmmnewssample.data.repository.AbstractApiRepository
import com.cerence.kmmnewssample.data.repository.ImplNewsApiRepository
import com.cerence.kmmnewssample.domain.usecase.NewsUseCase
import com.cerence.kmmnewssample.platformModule
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(
    baseUrl: String, enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    appDeclaration()
    modules(commonModule(baseUrl,enableNetworkLogs))
}

// called by iOS
fun initKoin(baseUrl: String) = initKoin(enableNetworkLogs = true, baseUrl = baseUrl) {}

fun commonModule(baseUrl: String,enableNetworkLogs: Boolean) =
    useCaseModule() + apiRepositoryModule(
    baseUrl = baseUrl, enableNetworkLogs = enableNetworkLogs
    ) + platformModule()

fun useCaseModule() = module {
    single {
        NewsUseCase(get())
    }
}

fun apiRepositoryModule(baseUrl: String, enableNetworkLogs: Boolean) = module {

    single<AbstractApiRepository> {
        ImplNewsApiRepository(get())
    }

    single<AbstractApiService> {
        ImplNewsApiService(get(), baseUrl)
    }

    single {
        createJson()
    }

    single {
        getHttpModule(get(), get(), enableNetworkLogs)
    }
}

private fun getHttpModule(
    httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean
) = HttpClient(engine = httpClientEngine) {
    install(ContentNegotiation) {
        json(json)
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }