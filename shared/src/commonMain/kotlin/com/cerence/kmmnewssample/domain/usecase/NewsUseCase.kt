package com.cerence.kmmnewssample.domain.usecase

import com.cerence.kmmnewssample.data.remote.dto.getDataModel
import com.cerence.kmmnewssample.data.repository.AbstractApiRepository
import kotlinx.coroutines.flow.flow

class NewsUseCase(
    private val repository: AbstractApiRepository
) {

    operator fun invoke(page:Int, pageSize:Int=20,country:String="us") = flow {
        val response = repository.callNewsApi(page,pageSize,country).getDataModel()
        emit(response)
    }
}