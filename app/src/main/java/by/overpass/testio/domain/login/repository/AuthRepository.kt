package by.overpass.testio.domain.login.repository

import by.overpass.testio.core.SimpleResult
import by.overpass.testio.domain.login.entity.UserCredentials

interface AuthRepository {

	suspend fun login(userCredentials: UserCredentials): SimpleResult
}