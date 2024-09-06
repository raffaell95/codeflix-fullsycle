import pytest

from src.django_project.category_app.models import Category
from src.django_project.category_app.repository import DjangoORMCategoryRepository
from src.core.genre.domain.genre import Genre
from src.django_project.genre_app.repository import DjangoORMGenreRepository
from src.django_project.genre_app.models import Genre as GenreORM


@pytest.mark.django_db
class TestSave:

    def test_saves_genre_in_database(self):
        genre = Genre(name="Action")
        genre_repository = DjangoORMGenreRepository()

        GenreORM.objects.count() == 0
        genre_repository.save(genre)

        assert GenreORM.objects.count() == 1
        genre_model = GenreORM.objects.first()
        assert genre_model.id == genre.id
        assert genre_model.name == "Action"
        assert genre_model.is_active is True
    
    def test_saves_genre_with_categories(self):
        repository = DjangoORMGenreRepository()
        category_repository = DjangoORMCategoryRepository()

        category = Category(name="Movie")
        category_repository.save(category)

        genre = Genre(name="Romance")
        genre.add_category(category.id)

        assert GenreORM.objects.count() == 0
        repository.save(genre)
        assert GenreORM.objects.count() == 1

        genre_model = GenreORM.objects.get(id=genre.id)
        related_category = genre_model.categories.get()
        assert related_category.id == category.id
        assert related_category.name == "Movie"