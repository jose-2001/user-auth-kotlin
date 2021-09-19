import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.KMongo
import com.mongodb.ConnectionString


val users = mutableListOf(
	User("seyerman","seyerman","Juan","Reyes","05/05/1995"),
	User("favellaneda","favellaneda","Fabio","Avellaneda","05/05/1995")
)

fun main() {
    embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }
        routing {
            get("/index.html") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            post("/check/index") {
                call.respondRedirect("/sign-in.html")
            }
            get("/sign-in.html") {
                call.respondText(
                    this::class.java.classLoader.getResource("sign-in.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            get("/sign-up.html") {
                call.respondText(
                    this::class.java.classLoader.getResource("sign-up.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            post("/save/index") {
                call.respondRedirect("/sign-up.html")
            }
            /**
            static("/") {
                resources("")
            }**/
        }


    }.start(wait = true)
}