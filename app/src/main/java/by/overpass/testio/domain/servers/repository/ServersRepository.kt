package by.overpass.testio.domain.servers.repository

import by.overpass.testio.domain.servers.entity.Server

interface ServersRepository {

	suspend fun getAllServers(): List<Server>
}