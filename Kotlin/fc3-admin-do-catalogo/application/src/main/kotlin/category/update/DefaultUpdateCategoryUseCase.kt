package com.raffa.admin.catalogo.application.category.update

import category.CategoryGateway
import category.CategoryID
import com.raffa.admin.catalogo.domain.category.Category
import exceptions.DomainException
import io.vavr.API
import io.vavr.control.Either
import validation.Error
import validation.handler.Notification
import java.util.Objects

class DefaultUpdateCategoryUseCase(
    private val categoryGateway: CategoryGateway
) : UpdateCategoryUseCase() {

    init {
        Objects.requireNonNull(categoryGateway)
    }

    override fun execute(aCommand: UpdateCategoryCommand): Either<Notification, UpdateCategoryOutput> {

        val aCategry = categoryGateway.findById(CategoryID.from(aCommand.id))
            .orElseThrow{ DomainException.with(Error(
                "Category with ID %s was not found".format(aCommand.id))) }

        val notification = Notification.create()

        aCategry.update(aCommand.name, aCommand.description, aCommand.isActive).validate(notification)

        return if(notification.hasError()) API.Left(notification) else update(aCategry)
    }

    private fun update(aCategory: Category): Either<Notification, UpdateCategoryOutput>{
        return API.Try { categoryGateway.update(aCategory) }
            .toEither()
            .bimap(Notification::create, UpdateCategoryOutput::from)
    }
}