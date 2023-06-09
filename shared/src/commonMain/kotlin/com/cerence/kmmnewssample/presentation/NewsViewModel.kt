package com.cerence.kmmnewssample.presentation

import com.cerence.kmmnewssample.domain.usecase.NewsUseCase
import com.cerence.kmmnewssample.domain.utils.asResult
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(private val newsUseCase: NewsUseCase): ViewModel() {

    private var _state = MutableStateFlow<NewsState>(NewsState.Idle)
    val state = _state.asStateFlow()
    private var page: Int = 1

    fun newsIntent(newsEvent: NewsEvent) {
        when(newsEvent) {
            is NewsEvent.GetHeadlines -> getHeadlines()
        }
    }

    private fun getHeadlines() {
        viewModelScope.launch {
            newsUseCase.invoke(page = page).asResult().collectLatest { result ->
                when(result) {
                    is com.cerence.kmmnewssample.domain.utils.Result.Error -> {
                        if (page == 1) {
                            _state.update { NewsState.Error(result.exception.message) }
                        }
                    }

                    is com.cerence.kmmnewssample.domain.utils.Result.Loading -> {
                        if (page == 1){
                            _state.update { NewsState.Loading }
                        }
                    }

                    is com.cerence.kmmnewssample.domain.utils.Result.Idle -> {
                        if (page == 1) {
                            _state.update { NewsState.Idle }
                        }
                    }

                    is com.cerence.kmmnewssample.domain.utils.Result.Success -> {
                        if (page == 1) {
                            _state.update {
                                NewsState.Success(result.data)
                            }
                        } else {
                            _state.update {
                                (it as NewsState.Success).copy(list = it.list + result.data)
                            }
                        }
                    }
                }
            }
        }
    }
}