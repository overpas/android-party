package by.overpass.testio.presentation.login

data class LoginState(
	val username: String = "",
	val password: String = "",
	val validationResult: CredentialsValidator.Result = CredentialsValidator.Result(
		usernameError = false,
		passwordError = false,
	),
	val isLoading: Boolean = false,
)