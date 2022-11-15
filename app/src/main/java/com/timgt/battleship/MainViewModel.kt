package com.timgt.battleship

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val battleshipClient: BattleshipClient = RemoteBattleshipClient()) : ViewModel() {

    private val _uiState: MutableStateFlow<MainState> = MutableStateFlow(MainState(isLoading = false, gameState = null))
    val uiState: StateFlow<MainState> = _uiState

    private val data: StateFlow<GameState?> = battleshipClient.messages.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    init {
        data
            .filterNotNull()
            .onEach { gameState ->
                println("Message received: $gameState")
                _uiState.update { it.copy(isLoading = false, gameState = gameState) }
            }
            .launchIn(viewModelScope)
    }

    fun findOpponentClicked() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch { battleshipClient.findOpponent() }
    }
}

data class MainState(
    val isLoading: Boolean,
    val gameState: GameState?
)
