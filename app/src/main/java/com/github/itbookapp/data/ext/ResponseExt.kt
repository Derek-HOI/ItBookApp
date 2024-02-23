package com.github.itbookapp.data.ext

import com.github.itbookapp.data.model.exception.CustomException
import retrofit2.Response

fun <T> Response<T>.verify(): T =
    if (this.isSuccessful) {
        this.body() ?: throw throwException(this)
    } else {
        throw throwException(this)
    }

fun <T> Response<List<T>>.verify(): List<T> =
    if (this.isSuccessful) {
        this.body() ?: listOf()
    } else {
        throw throwException(this)
    }

private fun <T> throwException(
    response: Response<T>
): CustomException =
    CustomException().apply {
        httpCode = response.code()
        httpMessage = response.message()
        errorBody = response.errorBody()?.string()
    }
