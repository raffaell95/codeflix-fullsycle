package category

import pagination.Pagination
import com.raffa.admin.catalogo.domain.category.Category
import java.util.Optional

interface CategoryGateway {

    fun create(aCategory: Category): Category

    fun deleteById(anId: CategoryID)

    fun findById(anId: CategoryID): Optional<Category>

    fun update(aCategory: Category): Category

    fun findAll(aQuery: CategorySearchQuery): Pagination<Category>
}