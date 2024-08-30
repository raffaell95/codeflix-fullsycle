package com.raffa.admin.catalogo.application.category.create

import category.CategoryGateway
import com.raffa.admin.catalogo.domain.category.Category
import io.vavr.API
import io.vavr.control.Either
import io.vavr.control.Either.Left
import validation.handler.Notification
import validation.handler.ThrowsValidationHandler
import java.util.*


class DefaultCreateCategoryUseCase(
    private var categoryGateway: CategoryGateway
) : CreateCategoryUseCase() {

    init {
        this.categoryGateway = Objects.requireNonNull(categoryGateway)
    }

    override fun execute(aCommand: CreateCategoryCommand): Either<Notification, CreateCategoryOutput> {
        val notification = Notification.create()

        val aCategory = Category.newCategory(aCommand.name, aCommand.description, aCommand.isActive)
        aCategory.validate(notification)

        return if(notification.hasError()) API.Left(notification) else create(aCategory)

    }

    private fun create(aCategory: Category): Either<Notification, CreateCategoryOutput>{
        return API.Try { categoryGateway.create(aCategory) }
            .toEither()
            .bimap(Notification::create, CreateCategoryOutput::from)
    }

}