package by.overpass.testio.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.overpass.testio.domain.servers.entity.Server

@Entity(tableName = "servers")
data class ServerEntity(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "server_id")
	val id: Long = 0,
	@ColumnInfo(name = "name")
	val name: String,
	@ColumnInfo(name = "distance")
	val distance: Double,
) {

	constructor(server: Server) : this(0L, server.name, server.distance)
}

fun ServerEntity.toServer(): Server = Server(name, distance)
