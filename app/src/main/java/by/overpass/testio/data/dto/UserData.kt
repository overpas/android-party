package by.overpass.testio.data.dto

import by.overpass.testio.domain.login.entity.UserCredentials
import com.google.gson.annotations.SerializedName

data class UserData(
	@SerializedName("username")
	val username: String,
	@SerializedName("password")
	val password: String,
)

fun UserCredentials.toUserData(): UserData = UserData(username, password)
