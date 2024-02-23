package com.github.itbookapp.domain.usecase

import com.github.itbookapp.data.model.RequestResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
abstract class FlowUseCase<P, R>(
    private val coroutineDispatcher: CoroutineDispatcher
) {
    protected abstract suspend fun execute(p: P): Flow<RequestResult<R>>

    suspend operator fun invoke(parameter: P): Flow<RequestResult<R>> =
        execute(parameter).flowOn(coroutineDispatcher)
}
