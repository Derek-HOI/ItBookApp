package com.github.itbookapp.domain.usecase

import com.github.itbookapp.data.model.RequestResult
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class BaseUseCase<P, R> {
    protected abstract suspend fun execute(p: P): RequestResult<R>

    suspend operator fun invoke(parameter: P): RequestResult<R> = execute(parameter)
}
