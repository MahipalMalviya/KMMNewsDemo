package com.cerence.kmmnewssample.presentation

sealed class NewsEvent {
    object GetHeadlines : NewsEvent()
}