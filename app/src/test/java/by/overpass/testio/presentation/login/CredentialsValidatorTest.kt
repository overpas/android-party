package by.overpass.testio.presentation.login

import by.overpass.testio.domain.login.entity.UserCredentials
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CredentialsValidatorTest(
	private val credentials: UserCredentials,
	private val validationResult: CredentialsValidator.Result,
) {

	companion object {

		@JvmStatic
		@Parameterized.Parameters
		fun data(): Collection<Array<Any>> {
			return listOf(
				arrayOf(
					UserCredentials("", ""),
					CredentialsValidator.Result(usernameError = true, passwordError = true),
				),
				arrayOf(
					UserCredentials("a", "a"),
					CredentialsValidator.Result(usernameError = false, passwordError = false),
				),
				arrayOf(
					UserCredentials("a", ""),
					CredentialsValidator.Result(usernameError = false, passwordError = true),
				),
				arrayOf(
					UserCredentials("", "a"),
					CredentialsValidator.Result(usernameError = true, passwordError = false),
				),
			)
		}
	}

	private val credentialsValidator = CredentialsValidator()

	@Test
	fun `validation result is correct`() {
		val result = credentialsValidator.validate(credentials)

		assertEquals(validationResult, result)
	}
}