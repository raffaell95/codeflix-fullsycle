package validation.handler

import exceptions.DomainException
import validation.Error
import validation.ValidationHandler

class ThrowsValidationHandler : ValidationHandler {

    override fun append(anError: Error): ValidationHandler {
        throw DomainException.with(anError)
    }

    override fun append(anHandler: ValidationHandler): ValidationHandler {
        throw DomainException.with(anHandler.getErrors())
    }

    override fun validate(aValidation: ValidationHandler.Validation): ValidationHandler {
        try {
            aValidation.validate()
        }catch (ex: Exception){
            throw DomainException.with(listOf(Error(ex.message.toString())))
        }
        return this
    }

    override fun getErrors(): MutableList<Error> {
        return mutableListOf()
    }
}