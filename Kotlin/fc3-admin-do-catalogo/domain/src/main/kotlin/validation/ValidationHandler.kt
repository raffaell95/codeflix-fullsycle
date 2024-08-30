package validation

interface ValidationHandler {

    fun append(anError: Error): ValidationHandler

    fun append(anHandler: ValidationHandler): ValidationHandler

    fun validate(aValidation: Validation): ValidationHandler

    fun getErrors(): MutableList<Error>

    fun firstError(): Error{
        return if(!getErrors().isNullOrEmpty()) getErrors().get(0) else null!!
    }

    fun hasError(): Boolean {
        return !getErrors().isNullOrEmpty() && !getErrors().isEmpty()
    }

    interface Validation{
        fun validate(){

        }
    }
}