import react.*
import react.dom.*
import kotlinext.js.*
import kotlinx.html.js.*
import kotlinx.coroutines.*

private val scope = MainScope()

val App = functionalComponent<RProps> { _ ->
    val (users, setUsers) = useState(emptyList<User>())

    useEffect {
        scope.launch {
            setUsers(getUsers())
        }
    }
    ul {
        users.forEach { item ->
            li {
                key = item.toString()
                +"[${item.username}] ${item.password} "
            }
        }
    }
}