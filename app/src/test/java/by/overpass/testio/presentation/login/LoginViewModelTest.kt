package by.overpass.testio.presentation.login

import by.overpass.testio.core.Result
import by.overpass.testio.domain.login.entity.UserCredentials
import by.overpass.testio.domain.login.usecase.LoginUseCase
import by.overpass.testio.domain.servers.usecase.FetchServersUseCase
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

	private val loginUseCase: LoginUseCase = mock()
	private val fetchServersUseCase: FetchServersUseCase = mock()
	private val credentialsValidator: CredentialsValidator = mock()

	private val viewModel = LoginViewModel(loginUseCase, fetchServersUseCase, credentialsValidator)

	private val testDispatcher = UnconfinedTestDispatcher()

	@Before
	fun setUp() {
		Dispatchers.setMain(testDispatcher)
	}

	@Test
	fun `username is empty initially`() = runTest {
		assertTrue(viewModel.state.value.username.isEmpty())
	}

	@Test
	fun `username is updated`() = runTest {
		viewModel.setUsername("username")

		val username = viewModel.state.value.username

		assertEquals("username", username)
	}

	@Test
	fun `validation result error removed when username is updated`() = runTest {
		viewModel.setUsername("username")

		val usernameError = viewModel.state.value.validationResult.usernameError

		assertFalse(usernameError)
	}

	@Test
	fun `password is empty initially`() = runTest {
		assertTrue(viewModel.state.value.password.isEmpty())
	}

	@Test
	fun `password is updated`() = runTest {
		viewModel.setPassword("password")

		val password = viewModel.state.value.password

		assertEquals("password", password)
	}

	@Test
	fun `validation result error removed when password is updated`() = runTest {
		viewModel.setPassword("password")

		val passwordError = viewModel.state.value.validationResult.passwordError

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

		viewModel.tryLogin()

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

		viewModel.tryLogin()

		assertEquals(validationResult, viewModel.state.value.validationResult)
	}

	@Test
	fun `servers fetched event sent when logging with valid and correct credentials`() = runTest {
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
		whenever(fetchServersUseCase()).thenReturn(emptyList())

		viewModel.tryLogin()

		val loginResult = viewModel.loginEvents.first()

		assertTrue(loginResult == LoginEvents.SERVERS_FETCHED)
	}

	@Test
	fun `fetch servers use case triggered when logging with valid and correct credentials`() = runTest {
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
		whenever(fetchServersUseCase()).thenReturn(emptyList())

		viewModel.tryLogin()

		verify(fetchServersUseCase).invoke()
	}

	@Test
	fun `login error event sent when logging with valid but incorrect credentials`() = runTest {
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

		viewModel.tryLogin()

		val loginResult = viewModel.loginEvents.first()

		assertTrue(loginResult == LoginEvents.LOGIN_ERROR)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}
}