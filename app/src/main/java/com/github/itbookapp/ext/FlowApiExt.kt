package com.github.itbookapp.ext

import android.util.Log
import com.github.itbookapp.data.model.RequestResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import java.net.UnknownHostException

private const val RetryTime = 3000L

fun <T> asFlowResponse(name: String, request: suspend () -> T): Flow<RequestResult<T>> =
    flow {
        emit(RequestResult.Loading)
        emit(RequestResult.Success(request()))
    }.retryWhen { cause, attempt ->
        if (cause is UnknownHostException) {
            delay(RetryTime)
            attempt < 3
        } else {
            false
        }
    }.catch {
        Log.e("asFlowResponse", "catch in $name, cause= $it")
        emit(RequestResult.Error(Exception(it)))
    }
