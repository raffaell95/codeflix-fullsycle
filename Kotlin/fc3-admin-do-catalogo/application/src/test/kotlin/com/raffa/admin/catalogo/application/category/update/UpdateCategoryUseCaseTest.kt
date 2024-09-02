package com.raffa.admin.catalogo.application.category.update

import category.CategoryGateway
import com.raffa.admin.catalogo.application.category.create.CreateCategoryCommand
import com.raffa.admin.catalogo.domain.category.Category
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.util.*
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class UpdateCategoryUseCaseTest {

    @InjectMocks
    private lateinit var useCase: DefaultUpdateCategoryUseCase

    @Mock
    private lateinit var categoryGateway: CategoryGateway

    @Test
    fun givenAValidCommand_whenCallUpdateCategory_shouldReturnCategoryId(){
        val aCategory = Category.newCategory("Film", null, true)

        val expectedId = aCategory.anId
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val aCommand = UpdateCategoryCommand.with(
            expectedId.getValue(),
            expectedName,
            expectedDescription,
            expectedIsActive
        )

        whenever(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory))

        whenever(categoryGateway.update(any())).thenAnswer { invocation ->
            invocation.getArgument(0)
        }

        val actualOutput = useCase.execute(aCommand).get()

        Assertions.assertNotNull((actualOutput))
        Assertions.assertNotNull(actualOutput.id)

        verify(categoryGateway, times(1)).findById(eq(expectedId))
        verify(categoryGateway, times(1)).update(argThat { aUpdatedCategory ->
                    expectedName == aUpdatedCategory.name &&
                    expectedDescription == aUpdatedCategory.description &&
                    expectedIsActive == aUpdatedCategory.isActive &&
                    aCategory.createdAt == aUpdatedCategory.createdAt
                    aCategory.updatedAt.isBefore(aUpdatedCategory.updatedAt)
                    aUpdatedCategory.deletedAt == null
        })

    }

    @Test
    fun givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException(){
        val aCategory = Category.newCategory("Film", null, true)

        val expectedId = aCategory.anId
        val expectedName: String = null ?: "  "
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true
        val expectedErrorMessage = "'name' should not be null or blank"
        val expectedErrorCount = 1

        val aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive)

        val notification = useCase.execute(aCommand).left

        // Assertions.assertEquals(expectedErrorCount, notification.getErrors().size)
        // Assertions.assertEquals(expectedErrorMessage, notification.firstError().message)

        verify(categoryGateway, times(0)).create(any())
    }
}