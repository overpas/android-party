package by.overpass.testio.di

import by.overpass.testio.data.datasource.LocalServersDataSource
import by.overpass.testio.data.datasource.LocalServersDataSourceImpl
import by.overpass.testio.data.datasource.RemoteServersDataSource
import by.overpass.testio.data.datasource.RemoteServersDataSourceImpl
import by.overpass.testio.data.repository.AuthRepositoryImpl
import by.overpass.testio.data.repository.ServersRepositoryImpl
import by.overpass.testio.domain.login.repository.AuthRepository
import by.overpass.testio.domain.servers.repository.ServersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

	@Binds
	@Singleton
	abstract fun localServersDataSource(localServersDataSourceImpl: LocalServersDataSourceImpl): LocalServersDataSource

	@Binds
	@Singleton
	abstract fun remoteServersDataSource(remoteServersDataSource: RemoteServersDataSourceImpl): RemoteServersDataSource

	@Binds
	@Singleton
	abstract fun authRepository(authRepository: AuthRepositoryImpl): AuthRepository

	@Binds
	@Singleton
	abstract fun serversRepository(serversRepositoryImpl: ServersRepositoryImpl): ServersRepository
}