package com.cerence.kmmnewssample.presentation

import com.cerence.kmmnewssample.domain.domain_model.NewsDataModel

sealed class NewsState{
    object Loading: NewsState()
    object Idle: NewsState()
    data class Success(val list: List<NewsDataModel>): NewsState()
    data class Error(val errorMsg: String): NewsState()
}
