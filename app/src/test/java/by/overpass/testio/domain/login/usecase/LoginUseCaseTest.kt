package by.overpass.testio.domain.login.usecase

import by.overpass.testio.domain.login.entity.UserCredentials
import by.overpass.testio.domain.login.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

	private val authRepository: AuthRepository = mock()

	private val useCase = LoginUseCase(authRepository)

	@Test
	fun `use case triggers login via repository`() = runTest {
		val credentials = UserCredentials("username", "password")

		useCase(credentials)

		verify(authRepository).login(credentials)
	}
}