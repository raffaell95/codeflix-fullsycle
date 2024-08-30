from dataclasses import dataclass
from typing import Union
from uuid import UUID
from src.core.category.domain.category_repository import CategoryRepository


@dataclass
class UpdateCategoryRequest:
    id: UUID
    name: str  = None # type: ignore
    description: str = None # type: ignore
    is_active: bool = None # type: ignore


class UpdateCategory:

    def __init__(self, repository: CategoryRepository) -> None:
        self.repository = repository

    def execute(self, request: UpdateCategoryRequest) -> None:
        category = self.repository.get_by_id(request.id)
        
        current_name = category.name
        current_description = category.description

        if request.name is not None:
            current_name = request.name
        
        if request.description is not None:
            current_description = request.description

        if request.is_active is True:
            category.activate()
        
        if request.is_active is False:
            category.deactivate()

        category.update_category(name=current_name, description=current_description)
        self.repository.update(category)