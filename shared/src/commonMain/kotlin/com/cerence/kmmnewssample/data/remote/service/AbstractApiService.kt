package com.cerence.kmmnewssample.data.remote.service

import com.cerence.kmmnewssample.data.remote.dto.NewsResponseDto

abstract class AbstractApiService {
    abstract suspend fun getNewsList(
        pageSize: Int,
        page: Int,
        country: String
    ): NewsResponseDto
}