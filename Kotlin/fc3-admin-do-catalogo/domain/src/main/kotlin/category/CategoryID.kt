package category

import Identifier
import java.util.UUID

class CategoryID(private val value: String): Identifier(){

    companion object{

        fun unique(): CategoryID{
            return from(UUID.randomUUID())
        }

        fun from(anId: String): CategoryID{
            return CategoryID(anId)
        }

        fun from(anId: UUID): CategoryID{
            return CategoryID(anId.toString().lowercase())
        }
    }

    fun getValue(): String{
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CategoryID) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}