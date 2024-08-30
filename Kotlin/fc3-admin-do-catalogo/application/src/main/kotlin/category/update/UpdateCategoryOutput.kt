package com.raffa.admin.catalogo.application.category.update

import category.CategoryID
import com.raffa.admin.catalogo.domain.category.Category

data class UpdateCategoryOutput(
    val id: CategoryID
){
    companion object{
        fun from(aCategory: Category): UpdateCategoryOutput{
            return UpdateCategoryOutput(aCategory.anId)
        }
    }
}