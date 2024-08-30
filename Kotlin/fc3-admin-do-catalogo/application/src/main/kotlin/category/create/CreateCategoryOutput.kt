package com.raffa.admin.catalogo.application.category.create

import category.CategoryID
import com.raffa.admin.catalogo.domain.category.Category


data class CreateCategoryOutput(
    val id: CategoryID
){
    companion object{
        fun from(aCategory: Category): CreateCategoryOutput{
            return CreateCategoryOutput(aCategory.id)
        }
    }
}
