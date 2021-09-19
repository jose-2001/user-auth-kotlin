import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListItem(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val birthdate: String) {
}