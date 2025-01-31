package com.lmorda.homework.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmorda.homework.domain.DataRepository
import com.lmorda.homework.ui.details.DetailsContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SAVED_ID_KEY = "id"

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
) : ViewModel() {

    private val id: Long? = savedStateHandle[SAVED_ID_KEY]

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    init {
        id?.let { repoId ->
            viewModelScope.launch {
                try {
                    val githubRepo = dataRepository.getRepo(id = repoId)
                    _state.value = state.value.copy(
                        githubRepo = githubRepo,
                    )
                } catch (ex: Exception) {
                    _state.value = state.value.copy(
                        exception = ex
                    )
                }
            }
        }
    }
}
