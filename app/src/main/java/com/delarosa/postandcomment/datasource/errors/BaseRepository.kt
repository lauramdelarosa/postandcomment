package com.delarosa.postandcomment.datasource.errors

/*
aqui estan todas las posibles clases de error que pueden exitiir en el llamado de los endpoints
 */


sealed class Failure : Throwable() {
    abstract class ServiceError : Failure()

    override fun toString(): String {
        return super.message ?: super.toString()
    }
}

class NotFound : Failure.ServiceError()
class InvalidToken : Failure.ServiceError()
class UpdateRequired : Failure.ServiceError()
class PaymentRequired : Failure.ServiceError()
class WrongParameters : Failure.ServiceError()
class ServiceUnavailable : Failure.ServiceError()
class GenericError(val reason: Throwable? = null) : Failure.ServiceError()
class Forbidden : Failure.ServiceError()



