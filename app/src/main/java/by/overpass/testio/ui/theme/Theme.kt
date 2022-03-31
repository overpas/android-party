package by.overpass.testio.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
	primary = LightGreen,
	primaryVariant = LightGreen,
	secondary = LightGray,
	onBackground = DarkGray,
	onSecondary = DarkBlue,
)

@Composable
fun TestioTheme(content: @Composable () -> Unit) {
	MaterialTheme(
		colors = LightColorPalette,
		typography = Typography,
		shapes = Shapes,
		content = content,
	)
}