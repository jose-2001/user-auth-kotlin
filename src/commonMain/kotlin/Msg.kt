import kotlinx.serialization.Serializable

@Serializable
data class Msg(var msgSignIn: String,
               var msgSignUp: String) {
    companion object {
        const val path = "/msgs"
    }
}