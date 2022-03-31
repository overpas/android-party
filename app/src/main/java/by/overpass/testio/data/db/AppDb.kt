package by.overpass.testio.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import by.overpass.testio.data.db.dao.ServersDao
import by.overpass.testio.data.db.entity.ServerEntity

@Database(entities = [ServerEntity::class], version = 1)
abstract class AppDb: RoomDatabase() {

	abstract fun serversDao(): ServersDao

	companion object {
		const val NAME = "testio.db"
	}
}