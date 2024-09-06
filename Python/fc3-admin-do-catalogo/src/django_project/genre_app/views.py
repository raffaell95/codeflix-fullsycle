from uuid import UUID

from rest_framework import viewsets
from rest_framework.request import Request
from rest_framework.response import Response
from rest_framework.status import (
    HTTP_200_OK,
    HTTP_204_NO_CONTENT,
    HTTP_404_NOT_FOUND,
    HTTP_201_CREATED, HTTP_400_BAD_REQUEST,
)

from src.core.genre.application.use_cases.list_genre import (
    ListGenre
)
from src.core.genre.application.use_cases.exception import (
    GenreNotFound,
    InvalidGenre,
    RelatedCategoriesNotFound,
)
from src.django_project.category_app.repository import DjangoORMCategoryRepository
from src.django_project.genre_app.repository import DjangoORMGenreRepository
from src.django_project.genre_app.serializers import (
    ListGenreOutputSerializer
)


class GenreViewSet(viewsets.ViewSet):
    def list(self, request: Request) -> Response:
        use_case = ListGenre(repository=DjangoORMGenreRepository())
        output: ListGenre.Input = use_case.execute(ListGenre.Input())
        response_serializer = ListGenreOutputSerializer(output)

        return Response(
            status=HTTP_200_OK,
            data=response_serializer.data,
        )
