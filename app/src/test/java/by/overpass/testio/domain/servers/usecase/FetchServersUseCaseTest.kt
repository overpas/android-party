package by.overpass.testio.domain.servers.usecase

import by.overpass.testio.domain.servers.entity.Server
import by.overpass.testio.domain.servers.repository.ServersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class FetchServersUseCaseTest {

	private val serversRepository: ServersRepository = mock()

	private val useCase = FetchServersUseCase(serversRepository)

	@Test
	fun `servers fetched from repository`() = runTest {
		val servers = listOf(Server("server", 1.0))
		whenever(serversRepository.getAllServers()).thenReturn(servers)

		val actual = useCase.invoke()

		assertEquals(servers, actual)
	}
}