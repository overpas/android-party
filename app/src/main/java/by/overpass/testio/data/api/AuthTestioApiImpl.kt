package by.overpass.testio.data.api

import by.overpass.testio.data.dto.ServerData
import by.overpass.testio.data.dto.UserData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface AuthTestioApi {

	suspend fun login(userData: UserData)

	suspend fun getServers(): List<ServerData>
}

class AuthTestioApiImpl(
	private val testioApi: TestioApi,
) : AuthTestioApi {

	private var token: String? = null
	private val mutex = Mutex()

	override suspend fun login(userData: UserData) {
		val tokenData = testioApi.login(userData)
		mutex.withLock {
			token = tokenData.token
		}
	}

	override suspend fun getServers(): List<ServerData> {
		if (token != null) {
			return testioApi.getServers(token!!)
		} else {
			throw UnauthorizedException()
		}
	}
}