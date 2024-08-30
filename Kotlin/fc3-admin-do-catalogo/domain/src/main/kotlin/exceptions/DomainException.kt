package exceptions

import validation.Error

class DomainException private constructor(aMessage: String, anErrors: List<Error>):
    NoStacktraceException(aMessage){

    val errors: List<Error> = anErrors

    companion object{

        fun with(anError: Error): DomainException{
            return DomainException("", listOf(anError))
        }

        fun with(anErrors: List<Error>): DomainException{
            return DomainException("", anErrors)
        }
    }
}