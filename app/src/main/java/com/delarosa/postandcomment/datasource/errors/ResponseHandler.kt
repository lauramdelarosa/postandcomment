package com.delarosa.postandcomment.datasource.errors

import retrofit2.HttpException
import java.net.SocketTimeoutException

class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Result<T> {
        return Result.success(data)
    }

    fun <T : Any> handleException(e: Exception, typeStatus: TYPE_STATUS): Result<T> {
        return when (e) {
            is HttpException -> Result.error(checkStatus(e.code(), typeStatus), null)
            is SocketTimeoutException -> Result.error(
                checkStatus(ErrorCodes.SocketTimeOut.code, typeStatus), null
            )
            else -> Result.error(checkStatus(Int.MAX_VALUE, typeStatus), null)
        }
    }

    private fun checkStatus(status: Int?, typeStatus: TYPE_STATUS): Throwable {

        return when (status) {
            400 -> WrongParameters()
            402 -> PaymentRequired()
            401 -> InvalidToken()
            403 -> Forbidden()
            ErrorCodes.SocketTimeOut.code -> ServiceUnavailable()
            else -> {
                when (typeStatus) {
                    TYPE_STATUS.TYPE1 -> {
                        checkStatus2(status)
                    }
                    else -> {
                        checkStatus3(status)
                    }
                }
            }
        }
    }

    private fun checkStatus2(status: Int?): Throwable {
        return when (status) {
            404 -> NotFound()
            426 -> UpdateRequired()
            503 -> ServiceUnavailable()
            500 -> ServiceUnavailable()
            else -> GenericError()
        }
    }

    private fun checkStatus3(status: Int?): Throwable {
        return when (status) {
            404 -> NotFound()
            426 -> UpdateRequired()
            503 -> ServiceUnavailable()
            500 -> ServiceUnavailable()
            else -> GenericError()
        }
    }

}

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}
