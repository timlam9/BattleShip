package com.timgt.battleship

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<MainState> = MutableStateFlow(MainState(isLoading = false))
    val uiState: StateFlow<MainState> = _uiState

    fun findOpponentClicked() {
        _uiState.update {
            it.copy(isLoading = true)
        }
    }
}

data class MainState(val isLoading: Boolean)
