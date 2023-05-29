package com.cerence.kmmnewssample.data.repository

import com.cerence.kmmnewssample.data.remote.dto.NewsResponseDto
import com.cerence.kmmnewssample.data.remote.service.ImplNewsApiService

class ImplNewsApiRepository(private val newsApiService: ImplNewsApiService) : AbstractApiRepository() {

    override suspend fun callNewsApi(page: Int, pageSize: Int, country: String): NewsResponseDto {
        return newsApiService.getNewsList(pageSize,page,country)
    }

}