package com.cerence.kmmnewssample.data.remote.dto

import com.cerence.kmmnewssample.domain.domain_model.NewsDataModel

@kotlinx.serialization.Serializable
data class NewsResponseDto(
    val articles: List<Article>,
)

@kotlinx.serialization.Serializable
data class Article(
    val author: String? = "Anonymous",

    val content: String? = "",

    val description: String? = "",

    val publishedAt: String,

    val source: Source,

    val title: String,

    val url: String,

    val urlToImage: String? = ""
)

@kotlinx.serialization.Serializable
data class Source(

    val id: String? = "",

    val name: String
)

fun NewsResponseDto.getDataModel() = this.articles
    .toMutableList()
    .map {
        NewsDataModel(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            source = it.source,
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage
        )
    }