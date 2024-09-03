from unittest.mock import create_autospec
from core.category.domain.category import Category
from core.category.domain.category_repository import CategoryRepository
from core.genre.application.use_cases.create_genre import CreateGenre
from core.genre.domain.genre_repository import GenreRepository
import pytest

@pytest.fixture
def mock_genre_repository() -> GenreRepository:
    return create_autospec(GenreRepository)


@pytest.fixture
def movie_category() -> Category:
    return Category(name="Movie")


@pytest.fixture
def documentary_category() -> Category:
    return Category(name="Documentary")


@pytest.fixture
def mock_category_repository_with_categories(movie_category, documentary_category) -> CategoryRepository:
    repository = create_autospec(CategoryRepository)
    repository.list.return_value = [movie_category, documentary_category]
    return repository


@pytest.fixture
def mock_empty_category_repository() -> CategoryRepository:
    repository = create_autospec(CategoryRepository)
    repository.list.return_value = []
    return repository
class TestCreateGenre:

    def test_when_categories_do_not_exist_then_raise_related_categories_not_found(
            self,
            mock_empty_category_repository,
            mock_genre_repository
        ):
        use_case = CreateGenre(
            repository = mock_genre_repository,
            category_repository=mock_empty_category_repository
        )

        input = CreateGenre.Input(name="Action")

    def test_when_created_genre_is_invalid_then_raise_invalid_genre(self):
        pass

    def test_when_created_genre_is_valid_and_categories_exist_then_save_genre(sefl):
        pass

    def test_create_genre_without_categories(self):
        pass