package by.overpass.testio.data.repository

import by.overpass.testio.data.datasource.LocalServersDataSource
import by.overpass.testio.data.datasource.RemoteServersDataSource
import by.overpass.testio.domain.servers.entity.Server
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ServersRepositoryImplTest {

	private val localServersDataSource: LocalServersDataSource = mock()
	private val remoteServersDataSource: RemoteServersDataSource = mock()

	private val repository = ServersRepositoryImpl(localServersDataSource, remoteServersDataSource)

	private val servers = listOf(
		Server("server1", 1.0),
		Server("server2", 2.0),
	)

	@Test
	fun `cached servers returned when data present in local data source`() = runTest {
		whenever(localServersDataSource.getAllServers()).thenReturn(servers)

		val actual = repository.getAllServers()

		assertEquals(servers, actual)
	}

	@Test
	fun `remote data source not called when data present in local data source`() = runTest {
		whenever(localServersDataSource.getAllServers()).thenReturn(servers)

		repository.getAllServers()

		verifyNoInteractions(remoteServersDataSource)
	}

	@Test
	fun `servers fetched from remote data source when data absent in local data source`() = runTest {
		whenever(localServersDataSource.getAllServers()).thenReturn(emptyList())
		whenever(remoteServersDataSource.getAllServers()).thenReturn(servers)

		val actual = repository.getAllServers()

		assertEquals(servers, actual)
	}

	@Test
	fun `servers from remote saved to local data source when data absent in local data source`() = runTest {
		whenever(localServersDataSource.getAllServers()).thenReturn(emptyList())
		whenever(remoteServersDataSource.getAllServers()).thenReturn(servers)

		repository.getAllServers()

		verify(localServersDataSource).saveServers(servers)
	}
}