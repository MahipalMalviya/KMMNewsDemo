package com.cerence.kmmnewssample.data.repository

import com.cerence.kmmnewssample.data.remote.dto.NewsResponseDto
import com.cerence.kmmnewssample.data.remote.service.ImplNewsApiService

abstract class AbstractApiRepository {

    abstract suspend fun callNewsApi(page: Int,
                             pageSize: Int,
                             country: String): NewsResponseDto
}