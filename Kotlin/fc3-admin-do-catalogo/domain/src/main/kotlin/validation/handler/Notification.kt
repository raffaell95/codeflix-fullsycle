package validation.handler

import exceptions.DomainException
import validation.Error
import validation.ValidationHandler
import kotlin.collections.ArrayList

class Notification private constructor(
    private var errors: MutableList<Error>
) : ValidationHandler {

    companion object{
        fun create(): Notification{
            return Notification(ArrayList())
        }

        fun create(anError: Error): Notification{
            return Notification(ArrayList()).append(anError)
        }

        fun create(t: Throwable): Notification{
            return create(Error(t.message.toString()))
        }
    }

    override fun append(anError: Error): Notification {
        errors.add(anError)
        return this
    }

    override fun append(anHandler: ValidationHandler): Notification {
        this.errors.addAll(anHandler.getErrors())
        return this
    }

    override fun validate(aValidation: ValidationHandler.Validation): Notification {
        try {
            aValidation.validate()
        } catch (e: DomainException) {
            errors.addAll(e.errors)
        } catch (t: Throwable){
            errors.add(Error(t.message.toString()))
        }
        return this
    }

    override fun getErrors(): MutableList<Error> {
       return errors
    }

}