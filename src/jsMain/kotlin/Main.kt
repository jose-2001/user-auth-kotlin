import io.ktor.client.request.*
import react.child
import react.dom.render
import kotlinx.browser.document

fun main() {
    if(document.getElementById("messageSignUp")!=null){
        render(document.getElementById("messageSignUp")) {
            child(App2)
        }
    }
    if(document.getElementById("root")!=null){
        render(document.getElementById("root")) {
            child(App3)
        }
    }

}


