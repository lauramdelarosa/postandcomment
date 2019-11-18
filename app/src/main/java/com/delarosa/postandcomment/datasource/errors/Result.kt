package com.delarosa.postandcomment.datasource.errors

data class Result<out T>(
    val status: Status,
    val data: T?,
    val message: Throwable?
) {
    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: Throwable, data: T?): Result<T> {
            return Result(Status.ERROR, data, msg)
        }
    }
}
