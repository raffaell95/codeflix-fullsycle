from uuid import UUID
import pytest

from core.category.domain.category_repository import CategoryRepository
from core.category.infra.in_memory_category_repository import InMemoryCategoryRepository
from core.genre.application.use_cases.create_genre import CreateGenre
from core.genre.infra.in_memory_category_repository import InMemoryGenreRepository
from django_project.category_app.models import Category


@pytest.fixture
def movie_category() -> Category:
    return Category(name="Movie")


@pytest.fixture
def documentary_category() -> Category:
    return Category(name="Documentary")

@pytest.fixture
def category_repository(movie_category, documentary_category) -> CategoryRepository:
    return InMemoryCategoryRepository(
        categories=[movie_category, documentary_category]
    )

class TestCreateGenre:
    def test_create_genre_with_associated_categories(
        self, movie_category, documentary_category, category_repository):
        
        genre_repository = InMemoryGenreRepository()
        use_case = CreateGenre(
            repository=genre_repository,
            category_repository=category_repository
        )

        input = CreateGenre.Input(
            name="Action",
            category_ids={movie_category.id, documentary_category.id}
        )

        output = use_case.execute(input)

        assert isinstance(output.id, UUID)
        saved_genre = genre_repository.get_by_id(output.id)
        assert saved_genre.name == "Action"
        assert saved_genre.categories == {movie_category.id, documentary_category.id}
        assert saved_genre.is_active == True

