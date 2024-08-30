from unittest.mock import MagicMock, create_autospec
from uuid import UUID
import uuid

import pytest

from src.core.category.domain.category_repository import CategoryRepository
from src.core.category.application.use_cases.create_category import CreateCategory, CreateCategoryRequest, CreateCategoryResponse
from src.core.category.application.use_cases.exceptions import InvalidCategoryData
from src.core.category.application.use_cases.get_category import GetCategory, GetCategoryRequest, GetCategoryResponse
from src.core.category.domain.category import Category

class TestGetCategory:

    def test_return_found_category(self):
        category = Category(
            name="Filme",
            description="Categoria para filmes",
            is_active=True
        )
        mock_repository = create_autospec(CategoryRepository)
        mock_repository.get_by_id.return_value = category
        use_case = GetCategory(repository=mock_repository)
        request = GetCategoryRequest(
             id=category.id
        )

        response = use_case.execute(request)

        assert response == GetCategoryResponse(
            id=category.id,
            name="Filme",
            description="Categoria para filmes",
            is_active=True
        )