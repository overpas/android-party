package by.overpass.testio.data.api

import by.overpass.testio.data.dto.ServerData
import by.overpass.testio.data.dto.TokenData
import by.overpass.testio.data.dto.UserData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AuthTestioApiImplTest {

	private val testioApi: TestioApi = mock()

	private val authTestioApi = AuthTestioApiImpl(testioApi)

	private val userData = UserData("username", "password")

	@Test
	fun `login triggers testioApi`() = runTest {
		whenever(testioApi.login(userData)).thenReturn(TokenData("token"))

		authTestioApi.login(userData)

		verify(testioApi).login(userData)
	}

	@Test
	fun `servers fetched when logged in`() = runTest {
		val tokenData = TokenData("token")
		val servers = listOf(ServerData("name", 1.0))
		whenever(testioApi.login(userData)).thenReturn(tokenData)
		whenever(testioApi.getServers("token")).thenReturn(servers)

		authTestioApi.login(userData)

		val actual = authTestioApi.getServers()

		assertEquals(servers, actual)
	}

	@Test(expected = UnauthorizedException::class)
	fun `exception thrown when servers fetched without login`() = runTest {
		val servers = listOf(ServerData("name", 1.0))
		whenever(testioApi.getServers("token")).thenReturn(servers)

		authTestioApi.getServers()
	}
}