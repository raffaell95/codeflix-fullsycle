package com.raffa.admin.catalogo.domain.category

import AggregateRoot
import category.CategoryID
import category.CategoryValidator
import org.jetbrains.annotations.NotNull
import validation.ValidationHandler
import java.time.Instant
import java.util.UUID

data class Category(
    val anId: CategoryID,
    var name: String?,
    var description: String?,
    var isActive: Boolean,
    val createdAt: Instant,
    var updatedAt: Instant,
    var deletedAt: Instant?
) : AggregateRoot<CategoryID>(anId) {

    companion object{
        fun newCategory(aName: String?, aDescription: String?, isActive: Boolean): Category{
            val id = CategoryID.unique()
            val now = Instant.now()
            val deletedAt = if(isActive) null else now
            return Category(id, aName, aDescription, isActive, now, now, deletedAt)
        }
    }

    fun activate(): Category{
        deletedAt = null

        isActive = true
        updatedAt = Instant.now()
        return this
    }

    fun deactivate(): Category{
        if(deletedAt == null){
            deletedAt = Instant.now()
        }
        isActive = false
        updatedAt = Instant.now()
        return this
    }

    fun update(aName: String?, aDescription: String, isActive: Boolean): Category{

        if(isActive) activate() else deactivate()

        name = aName
        description = aDescription
        updatedAt = Instant.now()
        return this
    }

    override fun validate(handler: ValidationHandler) {
        CategoryValidator(this, handler).validate()
    }
}