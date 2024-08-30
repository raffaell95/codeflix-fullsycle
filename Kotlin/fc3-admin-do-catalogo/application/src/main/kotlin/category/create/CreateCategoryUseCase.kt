package com.raffa.admin.catalogo.application.category.create

import com.raffa.admin.catalogo.application.UseCase
import io.vavr.control.Either
import validation.handler.Notification


abstract class CreateCategoryUseCase :
    UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>>() {}