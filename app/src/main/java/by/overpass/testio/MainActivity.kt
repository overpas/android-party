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
import by.overpass.testio.login.ui.LoginScreen
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
	LoginScreen(Modifier.fillMaxSize())
}