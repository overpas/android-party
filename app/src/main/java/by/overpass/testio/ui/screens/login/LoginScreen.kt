package by.overpass.testio.ui.screens.login

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.overpass.testio.R
import by.overpass.testio.presentation.login.LoginEvents
import by.overpass.testio.presentation.login.LoginState
import by.overpass.testio.presentation.login.LoginViewModel
import by.overpass.testio.ui.screens.loading.LoadingScreen
import by.overpass.testio.ui.theme.Gray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
	modifier: Modifier = Modifier,
	viewModel: LoginViewModel = hiltViewModel(),
	onNavigateToServers: () -> Unit = {},
) {
	LoginBackground(modifier)
	val state by viewModel.state.collectAsState()
	if (state.isLoading) {
		LoadingScreen(modifier)
	} else {
		LoginContent(
			modifier = modifier,
			viewModel = viewModel,
			state = state,
		)
	}
	LoginEvents(viewModel.loginEvents, onNavigateToServers)
}

@Composable
fun LoginBackground(modifier: Modifier = Modifier) {
	Image(
		painter = painterResource(id = R.drawable.ic_bg),
		contentScale = ContentScale.Crop,
		contentDescription = null,
		modifier = modifier,
	)
}

@ExperimentalComposeUiApi
@Composable
fun LoginContent(
	state: LoginState,
	viewModel: LoginViewModel,
	modifier: Modifier = Modifier,
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = modifier
			.padding(start = 60.dp, end = 60.dp),
	) {
		LogoLight()
		Spacer(modifier = Modifier.height(100.dp))

		val validationResult = state.validationResult

		val username = state.username
		LoginTextField(
			label = stringResource(R.string.login_label_username),
			value = username,
			leadingIconRes = R.drawable.ic_username,
			modifier = Modifier.fillMaxWidth(),
			imeAction = ImeAction.Next,
			isError = validationResult.usernameError,
			onValueChange = {
				viewModel.setUsername(it)
			}
		)

		val password = state.password
		val keyboardController = LocalSoftwareKeyboardController.current
		Spacer(modifier = Modifier.height(12.dp))
		LoginTextField(
			label = stringResource(R.string.login_label_password),
			value = password,
			leadingIconRes = R.drawable.ic_lock,
			modifier = Modifier.fillMaxWidth(),
			visualTransformation = PasswordVisualTransformation(),
			imeAction = ImeAction.Done,
			keyboardActions = KeyboardActions(
				onDone = {
					viewModel.tryLogin()
					keyboardController?.hide()
				},
			),
			isError = validationResult.passwordError,
			onValueChange = {
				viewModel.setPassword(it)
			}
		)

		Spacer(modifier = Modifier.height(12.dp))
		LoginButton(
			modifier = Modifier
				.fillMaxWidth()
				.height(50.dp),
		) {
			viewModel.tryLogin()
			keyboardController?.hide()
		}
	}
}

@Composable
fun LogoLight(modifier: Modifier = Modifier) {
	Image(
		painter = painterResource(id = R.drawable.ic_logo_light),
		contentDescription = null,
		modifier = modifier.scale(3F),
	)
}

@Composable
fun LoginEvents(loginEvents: Flow<LoginEvents>, onNavigateToServers: () -> Unit) {
	val context = LocalContext.current
	LaunchedEffect(Unit) {
		loginEvents.collect {
			when (it) {
				LoginEvents.SERVERS_FETCHED -> onNavigateToServers()

				LoginEvents.LOGIN_ERROR     -> {
					Toast.makeText(context, R.string.login_error_message, Toast.LENGTH_LONG).show()
				}
			}
		}
	}
}

@Composable
fun LoginTextField(
	label: String,
	value: String,
	@DrawableRes
	leadingIconRes: Int,
	modifier: Modifier = Modifier,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	imeAction: ImeAction = ImeAction.None,
	keyboardActions: KeyboardActions = KeyboardActions(),
	isError: Boolean = false,
	onValueChange: (String) -> Unit,
) {
	Column(modifier) {
		TextField(
			value = value,
			onValueChange = onValueChange,
			label = {
				Text(text = label)
			},
			leadingIcon = {
				Icon(
					painter = painterResource(id = leadingIconRes),
					contentDescription = null,
					tint = Gray,
				)
			},
			colors = TextFieldDefaults.textFieldColors(
				unfocusedLabelColor = Gray,
				backgroundColor = MaterialTheme.colors.background,
				textColor = MaterialTheme.colors.onBackground,
			),
			shape = MaterialTheme.shapes.small,
			visualTransformation = visualTransformation,
			singleLine = true,
			keyboardOptions = KeyboardOptions(
				imeAction = imeAction,
			),
			keyboardActions = keyboardActions,
			isError = isError,
			modifier = Modifier.fillMaxWidth(),
		)
		if (isError) {
			Text(
				text = stringResource(id = R.string.login_field_empty),
				fontSize = MaterialTheme.typography.caption.fontSize,
				color = MaterialTheme.colors.error,
			)
		}
	}
}

@Composable
fun LoginButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
	Button(
		onClick = onClick,
		modifier = modifier,
	) {
		Text(text = stringResource(R.string.login_button))
	}
}