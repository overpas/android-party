package by.overpass.testio.login.ui

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import by.overpass.testio.core.Failure
import by.overpass.testio.core.SimpleResult
import by.overpass.testio.core.Success
import by.overpass.testio.presentation.login.LoginViewModel
import by.overpass.testio.ui.theme.Gray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(modifier: Modifier = Modifier, viewModel: LoginViewModel = hiltViewModel()) {
	LoginBackground(modifier)
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = modifier
			.padding(start = 60.dp, end = 60.dp),
	) {
		LogoLight()
		Spacer(modifier = Modifier.height(100.dp))

		val validationResult by viewModel.validationResult.collectAsState()

		LoginMessage(viewModel.loginEvent)

		val username by viewModel.username.collectAsState()
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

		val password by viewModel.password.collectAsState()
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
					viewModel.login()
					keyboardController?.hide()
				},
			),
			isError = validationResult.passwordError,
			onValueChange = {
				viewModel.setPassword(it)
			}
		)

		Spacer(modifier = Modifier.height(12.dp))
		Button(
			onClick = {
				viewModel.login()
				keyboardController?.hide()
			},
			modifier = Modifier
				.fillMaxWidth()
				.height(50.dp),
		) {
			Text(text = stringResource(R.string.login_button))
		}
	}
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

@Composable
fun LogoLight(modifier: Modifier = Modifier) {
	Image(
		painter = painterResource(id = R.drawable.ic_logo_light),
		contentDescription = null,
		modifier = modifier.scale(3F),
	)
}

@Composable
fun LoginMessage(loginEvent: Flow<SimpleResult>) {
	val context = LocalContext.current
	LaunchedEffect(Unit) {
		loginEvent.collect {
			@StringRes
			val messageRes = when (it) {
				is Success -> R.string.login_success_message
				is Failure -> R.string.login_error_message
			}
			Toast.makeText(context, messageRes, Toast.LENGTH_LONG).show()
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