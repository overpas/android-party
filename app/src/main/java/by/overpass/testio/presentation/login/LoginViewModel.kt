package by.overpass.testio.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.overpass.testio.core.SimpleResult
import by.overpass.testio.domain.login.entity.UserCredentials
import by.overpass.testio.domain.login.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val loginUseCase: LoginUseCase,
	private val credentialsValidator: CredentialsValidator,
) : ViewModel() {

	private val _username = MutableStateFlow("")
	val username: StateFlow<String> = _username

	private val _password = MutableStateFlow("")
	val password: StateFlow<String> = _password

	private val _validationResult = MutableStateFlow(
		CredentialsValidator.Result(
			usernameError = false,
			passwordError = false,
		)
	)
	val validationResult: StateFlow<CredentialsValidator.Result> = _validationResult

	private val _loginEvent = Channel<SimpleResult>()
	val loginEvent = _loginEvent.receiveAsFlow()

	fun setUsername(username: String) {
		_username.value = username
		_validationResult.value = _validationResult.value.copy(usernameError = false)
	}

	fun setPassword(password: String) {
		_password.value = password
		_validationResult.value = _validationResult.value.copy(passwordError = false)
	}

	fun login() {
		viewModelScope.launch {
			val credentials = UserCredentials(username.value, password.value)
			val validationResult = credentialsValidator.validate(credentials)
			_validationResult.value = validationResult
			if (validationResult.isValid) {
				val loginResult = loginUseCase(credentials)
				_loginEvent.send(loginResult)
			}
		}
	}
}