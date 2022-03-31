package by.overpass.testio.data.dto

import com.google.gson.annotations.SerializedName

data class TokenData(
	@SerializedName("token")
	val token: String,
)