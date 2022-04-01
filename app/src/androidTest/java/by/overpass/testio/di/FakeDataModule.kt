package by.overpass.testio.di

import by.overpass.testio.data.datasource.LocalServersDataSource
import by.overpass.testio.data.datasource.LocalServersDataSourceImpl
import by.overpass.testio.data.datasource.RemoteServersDataSource
import by.overpass.testio.data.datasource.RemoteServersDataSourceImpl
import by.overpass.testio.data.repository.FakeAuthRepository
import by.overpass.testio.data.repository.FakeServersRepository
import by.overpass.testio.domain.login.repository.AuthRepository
import by.overpass.testio.domain.servers.repository.ServersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
	components = [SingletonComponent::class],
	replaces = [DataModule::class]
)
abstract class FakeDataModule {

	@Binds
	@Singleton
	abstract fun fakeLocalServersDataSource(localServersDataSourceImpl: LocalServersDataSourceImpl): LocalServersDataSource

	@Binds
	@Singleton
	abstract fun fakeRemoteServersDataSource(remoteServersDataSource: RemoteServersDataSourceImpl): RemoteServersDataSource

	@Binds
	@Singleton
	abstract fun fakeAuthRepository(authRepository: FakeAuthRepository): AuthRepository

	@Binds
	@Singleton
	abstract fun fakeServersRepository(serversRepositoryImpl: FakeServersRepository): ServersRepository
}