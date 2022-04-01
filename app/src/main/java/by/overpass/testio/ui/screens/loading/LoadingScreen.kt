package by.overpass.testio.ui.screens.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.overpass.testio.R
import by.overpass.testio.ui.theme.TestioTheme

const val LOADING_SERVERS_SEMANTICS = "loading_servers"
val CircularProgressSemanticsKey = SemanticsPropertyKey<String>("CircularProgressSemanticsKey")

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
	Box(
		modifier = modifier,
		contentAlignment = Alignment.Center,
	) {
		CircularProgressIndicator(
			color = MaterialTheme.colors.background,
			modifier = Modifier
				.scale(4F)
				.semantics {
					set(CircularProgressSemanticsKey, LOADING_SERVERS_SEMANTICS)
				},
			strokeWidth = 1.dp
		)
	}
	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.Bottom,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Text(
			text = stringResource(id = R.string.loading_message),
			color = MaterialTheme.colors.background,
		)
		Spacer(modifier = Modifier.height(140.dp))
	}
}

@Preview
@Composable
fun PreviewLoadingScreen() {
	TestioTheme {
		LoadingScreen(Modifier.fillMaxSize())
	}
}
