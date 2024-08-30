package com.raffa.admin.catalogo.application

import category.CategoryID
import com.raffa.admin.catalogo.domain.category.Category
import java.time.Instant
import java.util.UUID

abstract class UseCase<IN, OUT>{

    abstract fun execute(aCommand: IN): OUT
}