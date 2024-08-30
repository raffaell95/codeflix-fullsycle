package category

import com.raffa.admin.catalogo.domain.category.Category
import validation.Error
import validation.ValidationHandler
import validation.Validator

private const val NAME_MAX_LENGTH = 255
private const val NAME_MIN_LENGTH = 3

class CategoryValidator(
    private val category: Category,
    aHandler: ValidationHandler
) : Validator(aHandler){

    override fun validate() {
        checkNameConstraints()
    }

    private fun checkNameConstraints(){

        if (category.name.isNullOrBlank()){
            validationHandler().append(Error("'name' should not be null or blank"))
        }

        val length = category.name!!.trim().length
        if(length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH){
            validationHandler().append(Error("'name' must be between 3 and 255 characters"))
        }

    }
}