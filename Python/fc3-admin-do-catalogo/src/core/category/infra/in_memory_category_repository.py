
from typing import List, Optional, Union
from uuid import UUID
from src.core.category.domain.category_repository import CategoryRepository
from src.core.category.domain.category import Category


class InMemoryCategoryRepository(CategoryRepository):

    def __init__(self, categories: Optional[List[Category]] = None) -> None:
        self.categories = categories or []

    def save(self, category):
        self.categories.append(category)

    def get_by_id(self, id: UUID) -> Union[Category, None]:
        for category in self.categories:
            if category.id == id:
                return category
        return None
    
    def delete(self, id: UUID) -> None:
        category = self.get_by_id(id)
        if category:
            self.categories.remove(category)

    def update(self, category: Category) -> None:
        old_category = self.get_by_id(category.id)
        if old_category:
            self.categories.remove(old_category)
            self.categories.append(category)
    
    def list(self) -> list[Category]:
        return [category for category in self.categories]