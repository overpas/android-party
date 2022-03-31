package by.overpass.testio.di

import by.overpass.testio.data.repository.AuthRepositoryImpl
import by.overpass.testio.domain.login.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

	@Binds
	@Singleton
	abstract fun authRepository(authRepository: AuthRepositoryImpl): AuthRepository
}