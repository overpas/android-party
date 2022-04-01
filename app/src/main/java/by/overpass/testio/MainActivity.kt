package by.overpass.testio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.overpass.testio.ui.screens.login.LoginScreen
import by.overpass.testio.ui.screens.servers.ServersScreen
import by.overpass.testio.ui.theme.TestioTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			TestioTheme {
				// A surface container using the 'background' color from the theme
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
					TestioApp()
				}
			}
		}
	}
}

@ExperimentalComposeUiApi
@Composable
fun TestioApp() {
	val systemUiController = rememberSystemUiController()
	SideEffect {
		systemUiController.setStatusBarColor(Color.White)
	}
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = Destinations.LOGIN) {
		composable(Destinations.LOGIN) {
			LoginScreen(
				modifier = Modifier.fillMaxSize(),
				onNavigateToServers = {
					navController.navigate(Destinations.SERVERS) {
						popUpTo(Destinations.LOGIN) {
							inclusive = true
						}
					}
				}
			)
		}
		composable(Destinations.SERVERS) {
			ServersScreen(
				modifier = Modifier.fillMaxSize(),
				onLogout = {
					navController.navigate(Destinations.LOGIN) {
						popUpTo(Destinations.SERVERS) {
							inclusive = true
						}
					}
				}
			)
		}
	}
}