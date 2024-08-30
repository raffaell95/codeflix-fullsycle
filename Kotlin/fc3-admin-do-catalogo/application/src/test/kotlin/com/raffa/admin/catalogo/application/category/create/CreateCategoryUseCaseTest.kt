package com.raffa.admin.catalogo.application.category.create

import category.CategoryGateway
import exceptions.DomainException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class CreateCategoryUseCaseTest {

    @InjectMocks
    private lateinit var useCase: DefaultCreateCategoryUseCase

    @Mock
    private lateinit var categoryGateway: CategoryGateway

    @Test
    fun givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId(){

        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive)

        whenever(categoryGateway.create(any())).thenAnswer { invocation ->
            invocation.getArgument(0)
        }

        val actualOutput = useCase.execute(aCommand).get()

        Assertions.assertNotNull(actualOutput)
        Assertions.assertNotNull(actualOutput.id)

        verify(categoryGateway, times(1)).create(argThat { aCategory ->
                    expectedName == aCategory.name &&
                    expectedDescription == aCategory.description &&
                    expectedIsActive == aCategory.isActive &&
                    aCategory.deletedAt == null
        })
    }

    @Test
    fun givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException(){
        val expectedName: String = null ?: "  "
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true
        val expectedErrorMessage = "'name' should not be null or blank"
        val expectedErrorCount = 1

        val aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive)

        val notification = useCase.execute(aCommand).left

      // Assertions.assertEquals(expectedErrorCount, notification.getErrors().size)
      // Assertions.assertEquals(expectedErrorMessage, notification.firstError().message)

        verify(categoryGateway, times(0)).create(any())
    }

    @Test
    fun givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId(){

        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = false

        val aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive)

        whenever(categoryGateway.create(any())).thenAnswer { invocation ->
            invocation.getArgument(0)
        }

        val actualOutput = useCase.execute(aCommand).get()

        Assertions.assertNotNull(actualOutput)
        Assertions.assertNotNull(actualOutput.id)

        verify(categoryGateway, times(1)).create(argThat { aCategory ->
            expectedName == aCategory.name &&
                    expectedDescription == aCategory.description &&
                    expectedIsActive == aCategory.isActive &&
                    aCategory.deletedAt != null
        })
    }

    @Test
    fun givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnException(){

        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true
        val expectedErrorCount = 1
        val expectedErrorMessage = "Gateway error"

        val aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive)

        whenever(categoryGateway.create(any())).thenThrow(IllegalStateException(expectedErrorMessage))

        val notification = useCase.execute(aCommand).left

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size)
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message)

        verify(categoryGateway, times(1)).create(argThat { aCategory ->
            expectedName == aCategory.name &&
                    expectedDescription == aCategory.description &&
                    expectedIsActive == aCategory.isActive &&
                    aCategory.deletedAt == null
        })

    }
}