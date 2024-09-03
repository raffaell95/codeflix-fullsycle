from dataclasses import dataclass, field
from uuid import UUID


class CreateGenre:
    def __init__(self, repository, category_repository):
        self.repository = repository
        self.category_repository = category_repository

    @dataclass
    class Input:
        name: str
        category_ids: set[UUID] = field(default_factory=set)
        is_active: bool = True
    
    @dataclass
    class Output:
        id: UUID

    def execute(self, input: Input):
        pass