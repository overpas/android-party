package by.overpass.testio.presentation.servers

import by.overpass.testio.domain.servers.entity.Server

data class ServersState(
	val servers: List<Server> = emptyList(),
)