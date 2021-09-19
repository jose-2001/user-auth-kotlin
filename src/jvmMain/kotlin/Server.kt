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
            route(User.path) {
                get {
                    call.respond(users)
                }
                post {
                    users += call.receive<User>()
                    call.respond(HttpStatusCode.OK)
                }
            }
            get("/index.html") {
                var a = Msg("", "")
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }

            static("/") {
            resources("")
            }

            post("/check/index") {
                val params = call.receiveParameters()
                val username: String = params["username1"].toString()
                val password: String = params["pwd2"].toString()
                for(user in users){
                    if(user.username == username){
                        if(user.password==password){
                            call.respondRedirect("/sign-in.html")

                        }else{
                            call.respondRedirect("/index.html")
                        }
                    }
                }
                call.respondRedirect("/index.html")
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

                val params = call.receiveParameters()
                val username: String = params["Username"].toString()
                val password: String = params["pwd"].toString()
                val password1: String = params["pwd1"].toString()
                val fName: String = params["fName"].toString()
                val lName: String = params["lName"].toString()
                val bDate: String = params["bDate"].toString()
                var success = true
                if(password != password1){
                    //display message passwords don't match
                    success = false
                }
                var exists =false
                for (user in users){
                    if(user.username==username){
                        exists=true
                    }
                }
                if(exists){
                    //display message username exists
                    success = false
                }
                if(success) {
                    users.add(User(username, password, fName, lName, bDate))
                    call.respondRedirect("/index.html")
                }
            }

        }


    }.start(wait = true)
}