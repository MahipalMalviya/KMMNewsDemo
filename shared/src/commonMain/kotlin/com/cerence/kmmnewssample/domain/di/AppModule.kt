package com.cerence.kmmnewssample.domain.di

import com.cerence.kmmnewssample.data.remote.service.AbstractApiService
import com.cerence.kmmnewssample.data.remote.service.ImplNewsApiService
import com.cerence.kmmnewssample.data.repository.AbstractApiRepository
import com.cerence.kmmnewssample.data.repository.ImplNewsApiRepository
import com.cerence.kmmnewssample.domain.usecase.NewsUseCase
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(baseUrl: String, appDeclaration: KoinAppDeclaration = {}) = startKoin {
    modules(appModule(baseUrl))
}

private fun appModule(baseUrl: String) = apiRepositoryModule(baseUrl)

fun getApiKey() = "1acfaefcb4414f0eb1afb2f52aed9547"

private fun useCaseModule() = module {

}

private fun apiRepositoryModule(baseUrl: String) = module {

    single {
        NewsUseCase(get())
    }

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
        getHttpModule(get(),get(),true)
    }
}

private fun getHttpModule(
    httpClientEngine: HttpClientEngine,
    json: Json,
    enableNetworkLogs: Boolean
) {
    HttpClient(engine = httpClientEngine) {
        install(ContentNegotiation) {
            json(json)
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}

private fun createJson() {
    Json { isLenient = true;ignoreUnknownKeys = true }
}