package com.cerence.kmmnewssample.domain.domain_model

import com.cerence.kmmnewssample.data.remote.dto.Source

data class NewsDataModel(
    val author: String?,

    val content: String?,

    val description: String?,

    val publishedAt: String,

    val source: Source,

    val title: String,

    val url: String,

    val urlToImage: String?
)