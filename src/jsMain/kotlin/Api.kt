import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window

val endpoint = window.location.origin // only needed until https://youtrack.jetbrains.com/issue/KTOR-453 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getUsers(): List<User> {
    return jsonClient.get(endpoint + User.path)
}

suspend fun addUser(shoppingListItem: User) {
    jsonClient.post<Unit>(endpoint + User.path) {
        contentType(ContentType.Application.Json)
        body = shoppingListItem
    }
}
