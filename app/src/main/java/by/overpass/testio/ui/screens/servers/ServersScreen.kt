package by.overpass.testio.ui.screens.servers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.overpass.testio.R
import by.overpass.testio.domain.servers.entity.Server
import by.overpass.testio.presentation.servers.ServersViewModel
import by.overpass.testio.ui.theme.Gray
import by.overpass.testio.ui.theme.GrayBlue

@Composable
fun ServersScreen(
	modifier: Modifier = Modifier,
	onLogout: () -> Unit = {},
	viewModel: ServersViewModel = hiltViewModel(),
) {
	Scaffold(
		topBar = {
			ServersTopAppBar(
				modifier = Modifier.fillMaxWidth(),
				onLogout = onLogout,
			)
		},
		modifier = modifier,
	) {
		Column {
			ServersHeader(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 16.dp, vertical = 12.dp),
			)
			val state by viewModel.state.collectAsState()
			ServersList(
				servers = state.servers,
				modifier = Modifier.fillMaxWidth(),
			)
		}
	}
}

@Composable
fun ServersTopAppBar(modifier: Modifier = Modifier, onLogout: () -> Unit) {
	TopAppBar(
		backgroundColor = MaterialTheme.colors.secondary,
		contentPadding = PaddingValues(horizontal = 16.dp),
		modifier = modifier,
	) {
		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.fillMaxWidth(),
		) {
			Image(
				painter = painterResource(id = R.drawable.ic_logo_dark),
				contentDescription = null,
				modifier = Modifier
					.scale(2.5F)
					.padding(start = 14.dp),
			)
			Icon(
				painter = painterResource(id = R.drawable.ic_logout),
				contentDescription = stringResource(id = R.string.servers_logout),
				tint = MaterialTheme.colors.onSecondary,
				modifier = Modifier
					.size(20.dp)
					.clickable {
						onLogout()
					},
			)
		}
	}
}

@Composable
fun ServersHeader(modifier: Modifier = Modifier) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = modifier,
	) {
		Text(
			text = stringResource(id = R.string.servers_header_server),
			color = Gray,
			fontSize = MaterialTheme.typography.body2.fontSize,
		)
		Text(
			text = stringResource(id = R.string.servers_header_distance),
			color = Gray,
			fontSize = MaterialTheme.typography.body2.fontSize,
		)
	}
}

@Composable
fun ServersList(servers: List<Server>, modifier: Modifier = Modifier) {
	Box(
		modifier = modifier.background(
			brush = Brush.verticalGradient(
				0f to GrayBlue,
				10f to MaterialTheme.colors.background,
				100f to MaterialTheme.colors.background,
				startY = 0.0f,
				endY = 100.0f,
			)
		)
	) {
		LazyColumn(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp),
		) {
			itemsIndexed(servers) { index, item ->
				ServerItem(
					server = item,
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 16.dp),
				)
				if (index < servers.lastIndex) {
					Divider(color = MaterialTheme.colors.onBackground, thickness = 1.dp)
				}
			}
		}
	}
}

@Composable
fun ServerItem(server: Server, modifier: Modifier = Modifier) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = modifier,
	) {
		Text(
			text = server.name,
			fontSize = MaterialTheme.typography.body2.fontSize,
		)
		Text(
			text = stringResource(id = R.string.servers_server_distance, server.distance.toInt()),
			fontSize = MaterialTheme.typography.body2.fontSize,
		)
	}
}