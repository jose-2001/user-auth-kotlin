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
    users.forEach { item ->
        tr {
            key = (item.key).toString()
            td {
                +"${item.username}"
            }
            td {
                +"${item.lastName}"
            }
            td {
                +"${item.firstName}"
            }
            td {
                +"${item.birthdate}"
            }
        }
    }
}