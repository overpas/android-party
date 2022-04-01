package by.overpass.testio.data.repository

import by.overpass.testio.domain.servers.entity.Server
import by.overpass.testio.domain.servers.repository.ServersRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeServersRepository @Inject constructor() : ServersRepository {

	override suspend fun getAllServers(): List<Server> {
		return listOf(Server("server", 1.0))
	}
}