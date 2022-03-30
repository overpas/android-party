package by.overpass.testio.presentation.login

import by.overpass.testio.core.Failure
import by.overpass.testio.core.Result
import by.overpass.testio.core.Success
import by.overpass.testio.domain.login.entity.UserCredentials
import by.overpass.testio.domain.login.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

	private val loginUseCase: LoginUseCase = mock()
	private val credentialsValidator: CredentialsValidator = mock()

	private val viewModel = LoginViewModel(loginUseCase, credentialsValidator)

	private val testDispatcher = UnconfinedTestDispatcher()

	@Before
	fun setUp() {
		Dispatchers.setMain(testDispatcher)
	}

	@Test
	fun `username is empty initially`() = runTest {
		assertTrue(viewModel.username.value.isEmpty())
	}

	@Test
	fun `username is updated`() = runTest {
		viewModel.setUsername("username")

		val username = viewModel.username.value

		assertEquals("username", username)
	}

	@Test
	fun `validation result error removed when username is updated`() = runTest {
		viewModel.setUsername("username")

		val usernameError = viewModel.validationResult.value.usernameError

		assertFalse(usernameError)
	}

	@Test
	fun `password is empty initially`() = runTest {
		assertTrue(viewModel.password.value.isEmpty())
	}

	@Test
	fun `password is updated`() = runTest {
		viewModel.setPassword("password")

		val password = viewModel.password.value

		assertEquals("password", password)
	}

	@Test
	fun `validation result error removed when password is updated`() = runTest {
		viewModel.setPassword("password")

		val passwordError = viewModel.validationResult.value.passwordError

		assertFalse(passwordError)
	}

	@Test
	fun `login not performed when logging in with invalid credentials`() {
		whenever(credentialsValidator.validate(UserCredentials("", "")))
			.thenReturn(
				CredentialsValidator.Result(
					usernameError = true,
					passwordError = true,
				),
			)

		viewModel.login()

		verifyNoInteractions(loginUseCase)
	}

	@Test
	fun `validation result updated when logging in with invalid credentials`() {
		val validationResult = CredentialsValidator.Result(
			usernameError = true,
			passwordError = true,
		)
		whenever(credentialsValidator.validate(UserCredentials("", "")))
			.thenReturn(validationResult)

		viewModel.login()

		assertEquals(validationResult, viewModel.validationResult.value)
	}

	@Test
	fun `login successful when logging with valid and correct credentials`() = runTest {
		viewModel.setUsername("username")
		viewModel.setPassword("password")
		val userCredentials = UserCredentials("username", "password")
		whenever(credentialsValidator.validate(userCredentials))
			.thenReturn(
				CredentialsValidator.Result(
					usernameError = false,
					passwordError = false,
				),
			)
		whenever(loginUseCase(userCredentials)).thenReturn(Result.Success(Unit))

		viewModel.login()

		val loginResult = viewModel.loginEvent.first()

		assertTrue(loginResult is Success)
	}

	@Test
	fun `login unsuccessful when logging with valid but incorrect credentials`() = runTest {
		viewModel.setUsername("username")
		viewModel.setPassword("password")
		val userCredentials = UserCredentials("username", "password")
		whenever(credentialsValidator.validate(userCredentials))
			.thenReturn(
				CredentialsValidator.Result(
					usernameError = false,
					passwordError = false,
				),
			)
		whenever(loginUseCase(userCredentials)).thenReturn(Result.Failure(Unit))

		viewModel.login()

		val loginResult = viewModel.loginEvent.first()

		assertTrue(loginResult is Failure)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}
}