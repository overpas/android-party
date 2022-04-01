package by.overpass.testio.presentation.servers

import by.overpass.testio.domain.servers.entity.Server
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
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ServersViewModelTest {

	private val fetchServersUseCase: FetchServersUseCase = mock()

	private val viewModel by lazy { ServersViewModel(fetchServersUseCase) }

	private val testDispatcher = UnconfinedTestDispatcher()

	@Before
	fun setUp() {
		Dispatchers.setMain(testDispatcher)
	}

	@Test
	fun `servers loaded initially`() = runTest {
		val servers = listOf(
			Server("name1", 5.0),
			Server("name2", 10.0),
		)
		whenever(fetchServersUseCase()).thenReturn(servers)

		val state = viewModel.state.first()

		assertEquals(servers, state.servers)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}
}