package by.overpass.testio.di

import android.content.Context
import androidx.room.Room
import by.overpass.testio.data.db.AppDb
import by.overpass.testio.data.db.dao.ServersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

	@Provides
	@Singleton
	fun appDb(@ApplicationContext context: Context): AppDb {
		return Room.databaseBuilder(context, AppDb::class.java, AppDb.NAME)
			.fallbackToDestructiveMigration()
			.build()
	}

	@Provides
	@Singleton
	fun serversDao(appDb: AppDb): ServersDao = appDb.serversDao()
}