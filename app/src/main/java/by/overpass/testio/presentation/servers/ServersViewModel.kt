package by.overpass.testio.presentation.servers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.overpass.testio.domain.servers.usecase.FetchServersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServersViewModel @Inject constructor(
	private val fetchServersUseCase: FetchServersUseCase,
): ViewModel() {

	private val _state = MutableStateFlow(ServersState())
	val state: StateFlow<ServersState> = _state

	init {
		viewModelScope.launch {
			val servers = fetchServersUseCase()
			_state.update {
				it.copy(servers = servers)
			}
		}
	}
}