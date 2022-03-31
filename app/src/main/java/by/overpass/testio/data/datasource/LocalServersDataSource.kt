package by.overpass.testio.data.datasource

import by.overpass.testio.data.db.dao.ServersDao
import by.overpass.testio.data.db.entity.ServerEntity
import by.overpass.testio.data.db.entity.toServer
import by.overpass.testio.domain.servers.entity.Server
import javax.inject.Inject

interface LocalServersDataSource {

	suspend fun getAllServers(): List<Server>

	suspend fun saveServers(servers: List<Server>)
}

class LocalServersDataSourceImpl @Inject constructor(
	private val serversDao: ServersDao,
) : LocalServersDataSource {

	override suspend fun getAllServers(): List<Server> = serversDao.getAllServers()
		.map { it.toServer() }

	override suspend fun saveServers(servers: List<Server>) {
		val serverEntities = servers.map { ServerEntity(it) }
		serversDao.saveServers(serverEntities)
	}
}