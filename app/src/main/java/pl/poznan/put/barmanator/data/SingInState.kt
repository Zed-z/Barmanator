package pl.poznan.put.barmanator.data



data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)