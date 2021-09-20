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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


val users = mutableListOf(
	User("seyerman","seyerman","Juan","Reyes","05/05/1995", 1),
	User("favellaneda","favellaneda","Fabio","Avellaneda","05/05/1995", 2)
)
val msgs = mutableListOf(Msg("","", ""))
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
                msgs.get(0).msgSignUp=""
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            route(Msg.path) {
                get {
                    call.respond(msgs)
                }
                post {
                    users += call.receive<User>()
                    call.respond(HttpStatusCode.OK)
                }

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
                            msgs.get(0).msgTable=username
                        }else{
                            msgs.get(0).msgSignIn="User or password incorrect!"
                            call.respondRedirect("/index.html")
                        }
                    }
                }
                call.respondRedirect("/index.html")
                msgs.get(0).msgSignIn="User or password incorrect"
            }

            get("/sign-in.html") {
                msgs.get(0).msgSignIn=""
                call.respondText(
                    this::class.java.classLoader.getResource("sign-in.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            get("/sign-up.html") {
                msgs.get(0).msgSignIn=""
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
                if( username=="" || password=="" || password1=="" || fName=="" || lName=="" || bDate==""){
                    msgs.get(0).msgSignUp="Plis fill all the inputs!"
                    success = false
                }
                if(password != password1){
                    msgs.get(0).msgSignUp="The passwords don't match!"
                    success = false
                }
                var exists =false
                for (user in users){
                    if(user.username==username){
                        exists=true
                    }
                }
                if(exists){
                    msgs.get(0).msgSignUp="Username already exists!"
                    success = false
                }
                if(success) {
                    users.add(User(username, password, fName, lName, bDate, users.size+1))
                    msgs.get(0).msgSignIn="User created successfully!"
                    call.respondRedirect("/index.html")
                }
                else{call.respondRedirect("/sign-up.html")}
            }

        }


    }.start(wait = true)
}