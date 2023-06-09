package com.cerence.kmmnewssample.data.repository

import com.cerence.kmmnewssample.data.remote.dto.NewsResponseDto
import com.cerence.kmmnewssample.data.remote.service.AbstractApiService

class ImplNewsApiRepository(private val newsApiService: AbstractApiService) : AbstractApiRepository() {

    override suspend fun callNewsApi(page: Int, pageSize: Int, country: String): NewsResponseDto {
        return newsApiService.getNewsList(pageSize,page,country)
    }

}