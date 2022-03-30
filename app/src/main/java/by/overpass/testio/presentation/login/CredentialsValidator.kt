package by.overpass.testio.presentation.login

import by.overpass.testio.domain.login.entity.UserCredentials
import javax.inject.Inject

class CredentialsValidator @Inject constructor() {

	fun validate(userCredentials: UserCredentials): Result {
		return Result(
			userCredentials.username.isEmpty(),
			userCredentials.password.isEmpty(),
		)
	}

	data class Result(
		val usernameError: Boolean,
		val passwordError: Boolean,
	) {

		val isValid: Boolean = !usernameError && !passwordError
	}
}