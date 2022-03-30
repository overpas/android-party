package by.overpass.testio.data.repository

import by.overpass.testio.core.Result
import by.overpass.testio.core.SimpleResult
import by.overpass.testio.data.api.AuthTestioApi
import by.overpass.testio.data.dto.toUserData
import by.overpass.testio.domain.login.entity.UserCredentials
import by.overpass.testio.domain.login.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val authTestioApi: AuthTestioApi,
) : AuthRepository {

	@Suppress("SwallowedException")
	override suspend fun login(userCredentials: UserCredentials): SimpleResult {
		return try {
			authTestioApi.login(userCredentials.toUserData())
			Result.Success(Unit)
		} catch (e: HttpException) {
			Result.Failure(Unit)
		}
	}
}