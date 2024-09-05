from dataclasses import dataclass
from uuid import UUID

from src.core.genre.application.use_cases.exception import GenreNotFound
from src.core.genre.domain.genre_repository import GenreRepository


class DeleteGenre:
    def __init__(self, repository: GenreRepository) -> None:
        self.repository = repository
        
    @dataclass
    class Input:
        id: UUID

    def execute(self, input: Input) -> None:
        genre = self.repository.get_by_id(id=input.id)

        if genre is None:
            raise GenreNotFound(f"Genre with id {input.id} not found")

        self.repository.delete(genre.id)
    