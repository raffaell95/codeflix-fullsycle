package category

data class CategorySearchQuery(
    val page: Int,
    val perPage: Int,
    val terms: String,
    val sort: String,
    val direction: String
)
