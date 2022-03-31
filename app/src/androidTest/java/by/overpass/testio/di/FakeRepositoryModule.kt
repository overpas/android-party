package by.overpass.testio.di

import by.overpass.testio.data.repository.FakeAuthRepository
import by.overpass.testio.domain.login.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
	components = [SingletonComponent::class],
	replaces = [RepositoryModule::class]
)
abstract class FakeRepositoryModule {

	@Binds
	@Singleton
	abstract fun authRepository(authRepository: FakeAuthRepository): AuthRepository
}