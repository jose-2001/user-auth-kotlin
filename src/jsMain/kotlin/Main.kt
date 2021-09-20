import io.ktor.client.request.*
import react.child
import react.dom.render
import kotlinx.browser.document

fun main() {
    if(document.getElementById("dataUsers")!=null){
        render(document.getElementById("dataUsers")) {
            child(App)
        }
    }
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
    if(document.getElementById("userNameTable")!=null){
        render(document.getElementById("userNameTable")) {
            child(App4)
        }
    }


}


