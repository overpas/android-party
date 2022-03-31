package by.overpass.testio.data.repository

import by.overpass.testio.data.datasource.LocalServersDataSource
import by.overpass.testio.data.datasource.RemoteServersDataSource
import by.overpass.testio.domain.servers.entity.Server
import by.overpass.testio.domain.servers.repository.ServersRepository
import javax.inject.Inject

class ServersRepositoryImpl @Inject constructor(
	private val localServersDataSource: LocalServersDataSource,
	private val remoteServersDataSource: RemoteServersDataSource,
) : ServersRepository {

	override suspend fun getAllServers(): List<Server> =
		localServersDataSource.getAllServers().ifEmpty {
			val remoteServers = remoteServersDataSource.getAllServers()
			localServersDataSource.saveServers(remoteServers)
			remoteServers
		}
}