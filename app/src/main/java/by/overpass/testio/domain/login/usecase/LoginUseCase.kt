package by.overpass.testio.domain.login.usecase

import by.overpass.testio.core.SimpleResult
import by.overpass.testio.domain.login.entity.UserCredentials
import by.overpass.testio.domain.login.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
	private val authRepository: AuthRepository,
) {

	suspend operator fun invoke(userCredentials: UserCredentials): SimpleResult {
		return authRepository.login(userCredentials)
	}
}