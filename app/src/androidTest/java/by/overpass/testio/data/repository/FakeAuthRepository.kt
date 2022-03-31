package by.overpass.testio.data.repository

import by.overpass.testio.core.Result
import by.overpass.testio.core.SimpleResult
import by.overpass.testio.domain.login.entity.UserCredentials
import by.overpass.testio.domain.login.repository.AuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeAuthRepository @Inject constructor() : AuthRepository {

	override suspend fun login(userCredentials: UserCredentials): SimpleResult {
		delay(200)
		return if (userCredentials.username == "tesonet" && userCredentials.password == "partyanimal") {
			Result.Success(Unit)
		} else {
			Result.Failure(Unit)
		}
	}
}