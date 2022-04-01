package by.overpass.testio.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.overpass.testio.core.Success
import by.overpass.testio.domain.login.entity.UserCredentials
import by.overpass.testio.domain.login.usecase.LoginUseCase
import by.overpass.testio.domain.servers.usecase.FetchServersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val loginUseCase: LoginUseCase,
	private val fetchServersUseCase: FetchServersUseCase,
	private val credentialsValidator: CredentialsValidator,
) : ViewModel() {

	private val _state = MutableStateFlow(LoginState())
	val state: StateFlow<LoginState> = _state

	private val _loginEvents = Channel<LoginEvents>()
	val loginEvents = _loginEvents.receiveAsFlow()

	fun setUsername(username: String) {
		_state.update {
			it.copy(
				username = username,
				validationResult = it.validationResult.copy(usernameError = false),
			)
		}
	}

	fun setPassword(password: String) {
		_state.update {
			it.copy(
				password = password,
				validationResult = it.validationResult.copy(passwordError = false),
			)
		}
	}

	fun tryLogin() {
		viewModelScope.launch {
			val username = _state.value.username
			val password = _state.value.password
			val credentials = UserCredentials(username, password)
			val validationResult = credentialsValidator.validate(credentials)
			_state.update {
				it.copy(validationResult = validationResult)
			}
			if (validationResult.isValid) {
				loginAndFetchServers(credentials)
			}
		}
	}

	private suspend fun loginAndFetchServers(credentials: UserCredentials) {
		_state.update {
			it.copy(isLoading = true)
		}
		val loginResult = loginUseCase(credentials)
		if (loginResult is Success) {
			fetchServersUseCase()
			_loginEvents.send(LoginEvents.SERVERS_FETCHED)
		} else {
			_loginEvents.send(LoginEvents.LOGIN_ERROR)
			_state.update {
				it.copy(isLoading = false)
			}
		}
	}
}