package by.overpass.testio.data.dto

import com.google.gson.annotations.SerializedName

class ServerData(
	@SerializedName("name")
	val name: String,
	@SerializedName("distance")
	val distance: Double,
)