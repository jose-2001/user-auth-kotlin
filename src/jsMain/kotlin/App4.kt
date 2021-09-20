import react.*
import react.dom.*
import kotlinext.js.*
import kotlinx.html.js.*
import kotlinx.coroutines.*

private val scope = MainScope()

val App4 = functionalComponent<RProps> {
    val (msgs, setMsgs) = useState(emptyList<Msg>())

    useEffect {
        scope.launch {
            setMsgs(getMsgs())
        }
    }
    msgs.forEach { item ->
        p {
            key = item.toString()
            +item.msgTable
        }
    }

}