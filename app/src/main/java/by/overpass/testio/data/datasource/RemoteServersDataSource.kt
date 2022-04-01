package by.overpass.testio.data.datasource

import by.overpass.testio.data.api.AuthTestioApi
import by.overpass.testio.data.dto.toServer
import by.overpass.testio.domain.servers.entity.Server
import javax.inject.Inject

interface RemoteServersDataSource {

	suspend fun getAllServers(): List<Server>
}

class RemoteServersDataSourceImpl @Inject constructor(
	private val authTestioApi: AuthTestioApi,
) : RemoteServersDataSource {

	override suspend fun getAllServers(): List<Server> {
		return authTestioApi.getServers().map { it.toServer() }
	}
}
