package com.raffa.admin.catalogo.application.category.update

import com.raffa.admin.catalogo.application.UseCase
import io.vavr.control.Either
import validation.handler.Notification

abstract class UpdateCategoryUseCase : UseCase<UpdateCategoryCommand,
        Either<Notification, UpdateCategoryOutput>>()