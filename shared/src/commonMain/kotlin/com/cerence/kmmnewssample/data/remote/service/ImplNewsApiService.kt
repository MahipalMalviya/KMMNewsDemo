package com.cerence.kmmnewssample.data.remote.service

import com.cerence.kmmnewssample.data.remote.dto.NewsResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ImplNewsApiService(
    private val httpClient: HttpClient,
    private val baseUrl: String) : AbstractApiService() {

    private val apiKey = "1acfaefcb4414f0eb1afb2f52aed9547"
    override suspend fun getNewsList(
        pageSize: Int,
        page: Int,
        country: String
    ): NewsResponseDto = httpClient.get("$baseUrl/${EndPoints.HEADLINES}") {
        header("x-api-key", apiKey)
        parameter("country", country)
        parameter("pageSize", pageSize)
        parameter("page", page)
    }.body()

}