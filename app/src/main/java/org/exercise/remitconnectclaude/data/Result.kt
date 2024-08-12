package org.exercise.remitconnectclaude.data


sealed class Result<T>(val data: T? = null, val throwable: Throwable? = null) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(message: Throwable, data: T? = null) : Result<T>(data, message)
}
