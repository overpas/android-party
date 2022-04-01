package by.overpass.testio.data.datasource

import by.overpass.testio.data.db.dao.ServersDao
import by.overpass.testio.data.db.entity.ServerEntity
import by.overpass.testio.domain.servers.entity.Server
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LocalServersDataSourceImplTest {

	private val serversDao: ServersDao = mock()

	private val localServersDataSource = LocalServersDataSourceImpl(serversDao)

	private val serverEntities = listOf(
		ServerEntity(name = "server1", distance = 5.0),
		ServerEntity(name = "server2", distance = 10.0),
	)
	private val servers = listOf(
		Server(name = "server1", distance = 5.0),
		Server(name = "server2", distance = 10.0),
	)

	@Test
	fun `all servers fetched from dao`() = runTest {
		whenever(serversDao.getAllServers()).thenReturn(serverEntities)

		val actual = localServersDataSource.getAllServers()

		assertEquals(servers, actual)
	}

	@Test
	fun `all servers saved to dao`() = runTest {
		localServersDataSource.saveServers(servers)

		verify(serversDao).saveServers(serverEntities)
	}
}