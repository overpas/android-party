package by.overpass.testio.data.datasource

import by.overpass.testio.data.api.AuthTestioApi
import by.overpass.testio.data.dto.ServerData
import by.overpass.testio.domain.servers.entity.Server
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RemoteServersDataSourceImplTest {

	private val authTestioApi: AuthTestioApi = mock()

	private val remoteServersDataSource = RemoteServersDataSourceImpl(authTestioApi)

	private val serverData = listOf(
		ServerData("server1", 5.0),
		ServerData("server2", 10.0),
	)
	private val servers = listOf(
		Server(name = "server1", distance = 5.0),
		Server(name = "server2", distance = 10.0),
	)

	@Test
	fun `servers fetched from api`() = runTest {
		whenever(authTestioApi.getServers()).thenReturn(serverData)

		val actual = remoteServersDataSource.getAllServers()

		assertEquals(servers, actual)
	}
}