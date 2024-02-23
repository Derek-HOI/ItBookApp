package com.github.itbookapp.data.model

sealed class RequestResult<out T> {
    data object Loading : RequestResult<Nothing>()
    data class Success<T>(val data: T) : RequestResult<T>()
    data class Error(val cause: Throwable) : RequestResult<Nothing>()
}

inline fun <reified T, reified R> RequestResult<T>.map(transform: (T) -> R): RequestResult<R> {
    return when (this) {
        is RequestResult.Success -> RequestResult.Success(transform(data))
        is RequestResult.Error -> this
        is RequestResult.Loading -> this
    }
}

inline fun <reified T, reified R> RequestResult<T>.mapNotNull(transform: (T) -> R?): RequestResult<R> {
    return when (this) {
        is RequestResult.Success -> {
            val ret = transform(data)
            ret?.let {
                RequestResult.Success(ret)
            } ?: RequestResult.Error(
                cause = IllegalStateException("transform data is null.")
            )
        }
        is RequestResult.Error -> this
        is RequestResult.Loading -> this
    }
}

inline fun <reified T> RequestResult<T>.onLoading(action: () -> Unit): RequestResult<T> {
    if (this is RequestResult.Loading) action()
    return this
}

inline fun <reified T> RequestResult<T>.onSuccess(action: (data: T) -> Unit): RequestResult<T> {
    if (this is RequestResult.Success) action(data)
    return this
}

inline fun <reified T> RequestResult<T>.onError(action: (cause: Throwable) -> Unit): RequestResult<T> {
    if (this is RequestResult.Error) action(cause)
    return this
}

inline fun <reified R> RequestResult<R>.extract(): R? {
    return when (this) {
        is RequestResult.Success -> data
        is RequestResult.Error, RequestResult.Loading -> null
    }
}
