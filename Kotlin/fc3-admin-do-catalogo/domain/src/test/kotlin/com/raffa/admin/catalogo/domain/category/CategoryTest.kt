package com.raffa.admin.catalogo.domain.category

import exceptions.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import validation.handler.ThrowsValidationHandler

class CategoryTest {

    @Test
    fun givenAValidParams_whenCallNewCategory_thenInstantiateACategory(){
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val actualCategory =
            Category.newCategory(expectedName, expectedDescription, expectedIsActive)

        Assertions.assertNotNull(actualCategory)
        Assertions.assertNotNull(actualCategory.id)
        Assertions.assertEquals(expectedName, actualCategory.name)
        Assertions.assertEquals(expectedDescription, actualCategory.description)
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive)
        Assertions.assertNotNull(actualCategory.createdAt)
        Assertions.assertNotNull(actualCategory.updatedAt)
        Assertions.assertNull(actualCategory.deletedAt)
    }

    @Test
    fun givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        val expectedName: String? = null
        val expectedErrorCount = 1
        val expectedErrorMessage = "'name' should not be null or blank"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val actualCategory =
            Category.newCategory(expectedName, expectedDescription, expectedIsActive)

        val actualException =
            Assertions.assertThrows(
                DomainException::class.java
            ){ actualCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertEquals(expectedErrorCount, actualException.errors.size)
        Assertions.assertEquals(expectedErrorMessage, actualException.errors[0].message)
    }

    @Test
    fun givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        val expectedName = "   "
        val expectedErrorCount = 1
        val expectedErrorMessage = "'name' should not be null or blank"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val actualCategory =
            Category.newCategory(expectedName, expectedDescription, expectedIsActive)

        val actualException =
            Assertions.assertThrows(
                DomainException::class.java
            ){ actualCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertEquals(expectedErrorCount, actualException.errors.size)
        Assertions.assertEquals(expectedErrorMessage, actualException.errors[0].message)
    }

    @Test
    fun givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        val expectedName = "Fi "
        val expectedErrorCount = 1
        val expectedErrorMessage = "'name' must be between 3 and 255 characters"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val actualCategory =
            Category.newCategory(expectedName, expectedDescription, expectedIsActive)

        val actualException =
            Assertions.assertThrows(
                DomainException::class.java
            ){ actualCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertEquals(expectedErrorCount, actualException.errors.size)
        Assertions.assertEquals(expectedErrorMessage, actualException.errors[0].message)
    }

    @Test
    fun givenAnInvalidNameLengthLessThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        val expectedName = """
            A prática cotidiana prova que a execução dos pontos do programa não pode mais se dissociar do 
            orçamento setorial. A certificação de metodologias que nos auxiliam a lidar com a competitividade 
            nas transações comerciais desafia a capacidade de equalização do impacto na agilidade decisória. 
            Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se a adoção de políticas 
            descentralizadoras ainda não demonstrou convincentemente que vai participar na mudança das diversas 
            correntes de pensamento. Pensando mais a longo prazo, a estrutura atual da organização possibilita 
            uma melhor visão global das posturas dos órgãos dirigentes com relação às suas atribuições. 
            Do mesmo modo, a valorização de fatores subjetivos faz parte de um processo de gerenciamento 
            das novas proposições.
        """

        val expectedErrorCount = 1
        val expectedErrorMessage = "'name' must be between 3 and 255 characters"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val actualCategory =
            Category.newCategory(expectedName, expectedDescription, expectedIsActive)

        val actualException =
            Assertions.assertThrows(
                DomainException::class.java
            ){ actualCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertEquals(expectedErrorCount, actualException.errors.size)
        Assertions.assertEquals(expectedErrorMessage, actualException.errors[0].message)
    }

    @Test
    fun givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        val expectedName = "Filmes"
        val expectedDescription = "  "
        val expectedIsActive = true

        val actualCategory =
            Category.newCategory(expectedName, expectedDescription, expectedIsActive)

        Assertions.assertDoesNotThrow{ actualCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertNotNull(actualCategory)
        Assertions.assertNotNull(actualCategory.id)
        Assertions.assertEquals(expectedName, actualCategory.name)
        Assertions.assertEquals(expectedDescription, actualCategory.description)
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive)
        Assertions.assertNotNull(actualCategory.createdAt)
        Assertions.assertNotNull(actualCategory.updatedAt)
        Assertions.assertNull(actualCategory.deletedAt)
    }

    @Test
    fun givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        val expectedName = "Filmes"
        val expectedDescription = "A Categoria mais assistida"
        val expectedIsActive = false

        val actualCategory =
            Category.newCategory(expectedName, expectedDescription, expectedIsActive)

        Assertions.assertDoesNotThrow(){ actualCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertNotNull(actualCategory)
        Assertions.assertNotNull(actualCategory.id)
        Assertions.assertEquals(expectedName, actualCategory.name)
        Assertions.assertEquals(expectedDescription, actualCategory.description)
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive)
        Assertions.assertNotNull(actualCategory.createdAt)
        Assertions.assertNotNull(actualCategory.updatedAt)
        Assertions.assertNotNull(actualCategory.deletedAt)
    }

    @Test
    fun givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated(){
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = false

        val aCategory =
            Category.newCategory(expectedName, expectedDescription, true)

        val actualException =
            Assertions.assertDoesNotThrow { aCategory.validate(ThrowsValidationHandler()) }

        val createdAt = aCategory.createdAt
        val updatedAt = aCategory.updatedAt

        Assertions.assertTrue(aCategory.isActive)
        Assertions.assertNull(aCategory.deletedAt)

        val actualCategory = aCategory.deactivate()

        Assertions.assertDoesNotThrow(){ actualCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertEquals(aCategory.id, actualCategory.id)
        Assertions.assertEquals(expectedName, actualCategory.name)
        Assertions.assertEquals(expectedDescription, actualCategory.description)
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive)
        Assertions.assertEquals(createdAt, actualCategory.createdAt)
        Assertions.assertTrue(actualCategory.updatedAt.isAfter(updatedAt))
        Assertions.assertNotNull(actualCategory.deletedAt)
    }

    @Test
    fun givenAValidInctiveCategory_whenCallDeactivate_thenReturnCategoryActivated(){
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val aCategory =
            Category.newCategory(expectedName, expectedDescription, false)

        val actualException =
            Assertions.assertDoesNotThrow { aCategory.validate(ThrowsValidationHandler()) }

        val createdAt = aCategory.createdAt
        val updatedAt = aCategory.updatedAt

        Assertions.assertFalse(aCategory.isActive)
        Assertions.assertNotNull(aCategory.deletedAt)

        val actualCategory = aCategory.activate()

        Assertions.assertDoesNotThrow(){ actualCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertEquals(aCategory.id, actualCategory.id)
        Assertions.assertEquals(expectedName, actualCategory.name)
        Assertions.assertEquals(expectedDescription, actualCategory.description)
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive)
        Assertions.assertEquals(createdAt, actualCategory.createdAt)
        Assertions.assertTrue(actualCategory.updatedAt.isAfter(updatedAt))
        Assertions.assertNull(actualCategory.deletedAt)
    }

    @Test
    fun givenValidCategory_whenCallUpdate_thenReturnCategoryUpdated(){
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val aCategory =
            Category.newCategory("Film", "A Categoria", expectedIsActive)

        val actualException =
            Assertions.assertDoesNotThrow { aCategory.validate(ThrowsValidationHandler()) }

        val createdAt = aCategory.createdAt
        val updatedAt = aCategory.updatedAt

        val actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive)

        Assertions.assertDoesNotThrow(){ actualCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertEquals(aCategory.id, actualCategory.id)
        Assertions.assertEquals(expectedName, actualCategory.name)
        Assertions.assertEquals(expectedDescription, actualCategory.description)
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive)
        Assertions.assertEquals(createdAt, actualCategory.createdAt)
        Assertions.assertTrue(actualCategory.updatedAt.isAfter(updatedAt))
        Assertions.assertNull(actualCategory.deletedAt)
    }

    @Test
    fun givenValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated(){
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = false

        val aCategory =
            Category.newCategory("Film", "A Categoria", true)

        val actualException =
            Assertions.assertDoesNotThrow { aCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertTrue(aCategory.isActive)
        Assertions.assertNull(aCategory.deletedAt)

        val createdAt = aCategory.createdAt
        val updatedAt = aCategory.updatedAt

        val actualCategory = aCategory.update(expectedName, expectedDescription, false)

        Assertions.assertDoesNotThrow(){ actualCategory.validate(ThrowsValidationHandler()) }

        Assertions.assertEquals(aCategory.id, actualCategory.id)
        Assertions.assertEquals(expectedName, actualCategory.name)
        Assertions.assertEquals(expectedDescription, actualCategory.description)
        Assertions.assertFalse(aCategory.isActive)
        Assertions.assertEquals(createdAt, actualCategory.createdAt)
        Assertions.assertTrue(actualCategory.updatedAt.isAfter(updatedAt))
        Assertions.assertNotNull(actualCategory.deletedAt)
    }

    @Test
    fun givenValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated(){
        val expectedName: String? = null
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val aCategory =
            Category.newCategory("Filmes", "A Categoria", expectedIsActive)

        val actualException =
            Assertions.assertDoesNotThrow { aCategory.validate(ThrowsValidationHandler()) }

        val createdAt = aCategory.createdAt
        val updatedAt = aCategory.updatedAt

        val actualCategory = aCategory.update(expectedName, expectedDescription, true)

        Assertions.assertEquals(aCategory.id, actualCategory.id)
        Assertions.assertEquals(expectedName, actualCategory.name)
        Assertions.assertEquals(expectedDescription, actualCategory.description)
        Assertions.assertTrue(aCategory.isActive)
        Assertions.assertEquals(createdAt, actualCategory.createdAt)
        Assertions.assertTrue(actualCategory.updatedAt.isAfter(updatedAt))
        Assertions.assertNull(actualCategory.deletedAt)
    }
}