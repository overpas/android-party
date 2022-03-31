package by.overpass.testio.domain.servers.usecase

import by.overpass.testio.domain.servers.entity.Server
import by.overpass.testio.domain.servers.repository.ServersRepository
import javax.inject.Inject

class FetchServersUseCase @Inject constructor(
	private val serversRepository: ServersRepository,
) {

	suspend operator fun invoke(): List<Server> = serversRepository.getAllServers()
}