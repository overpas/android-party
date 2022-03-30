package by.overpass.testio.data.api

import by.overpass.testio.data.dto.ServerData
import by.overpass.testio.data.dto.TokenData
import by.overpass.testio.data.dto.UserData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

const val TIMEOUT_CONNECT_SEC = 30L
const val TIMEOUT_READ_SEC = 30L
const val TIMEOUT_WRITE_SEC = 30L

interface TestioApi {

	@Headers("Content-Type: application/json; charset=UTF-8")
	@POST("tokens")
	suspend fun login(@Body userData: UserData): TokenData

	@GET("servers")
	suspend fun getServers(@Header("Authorization") token: String): List<ServerData>

	companion object {

		const val BASE_URL = "https://playground.tesonet.lt/v1/"
	}
}

