package by.overpass.testio.data.repository

import by.overpass.testio.core.Failure
import by.overpass.testio.core.Success
import by.overpass.testio.data.api.AuthTestioApi
import by.overpass.testio.data.dto.UserData
import by.overpass.testio.domain.login.entity.UserCredentials
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class AuthRepositoryImplTest {

	private val authTestioApi: AuthTestioApi = mock()

	private val authRepository = AuthRepositoryImpl(authTestioApi)

	@Test
	fun `result is Success when login successful`() = runTest {
		val userCredentials = UserCredentials("username", "password")
		whenever(authTestioApi.login(UserData("username", "password"))).thenReturn(Unit)

		val result = authRepository.login(userCredentials)

		assertTrue(result is Success)
	}

	@Test
	fun `result is Failure when login unsuccessful with 401`() = runTest {
		val userCredentials = UserCredentials("username", "password")
		whenever(authTestioApi.login(UserData("username", "password"))).thenAnswer {
			throw HttpException(
				Response.error<String>(
					401,
					"{}".toResponseBody(),
				),
			)
		}

		val result = authRepository.login(userCredentials)

		assertTrue(result is Failure)
	}

	@Test(expected = HttpException::class)
	fun `result is Failure when login unsuccessful with 404`() = runTest {
		val userCredentials = UserCredentials("username", "password")
		whenever(authTestioApi.login(UserData("username", "password"))).thenAnswer {
			throw HttpException(
				Response.error<String>(
					404,
					"{}".toResponseBody(),
				),
			)
		}

		authRepository.login(userCredentials)
	}
}