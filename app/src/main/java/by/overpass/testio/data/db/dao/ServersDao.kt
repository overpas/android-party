package by.overpass.testio.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.overpass.testio.data.db.entity.ServerEntity

@Dao
interface ServersDao {

	@Query("SELECT * FROM servers")
	suspend fun getAllServers(): List<ServerEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun saveServers(servers: List<ServerEntity>)

	@Query("DELETE FROM servers")
	suspend fun deleteServers()
}