package by.overpass.testio.data.dto

import by.overpass.testio.domain.servers.entity.Server
import com.google.gson.annotations.SerializedName

class ServerData(
	@SerializedName("name")
	val name: String,
	@SerializedName("distance")
	val distance: Double,
)

fun ServerData.toServer(): Server = Server(name, distance)