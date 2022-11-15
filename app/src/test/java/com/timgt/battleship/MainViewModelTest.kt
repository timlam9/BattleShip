package com.timgt.battleship

import app.cash.turbine.test
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `find opponent`() = runTest {
        val battleshipClient = FakeBattleshipClient()
        val viewModel = MainViewModel(battleshipClient)
        val gameState = GameState(
            stage = Stage.Setup,
            myBoard = emptyBoard
        )

        viewModel.uiState.test {
            viewModel.findOpponentClicked()
            assertEquals(mainState(isLoading = false), awaitItem())
            assertEquals(mainState(isLoading = true), awaitItem())
            assertEquals(mainState(isLoading = false, gameState = gameState), awaitItem())
            ensureAllEventsConsumed()
        }
    }
}

class FakeBattleshipClient : BattleshipClient {

    private val _messages = MutableSharedFlow<GameState>()
    override val messages: SharedFlow<GameState> = _messages

    override suspend fun findOpponent() {
        val gameState = GameState(
            stage = Stage.Setup,
            myBoard = emptyBoard
        )
       _messages.emit(gameState)
    }

    override suspend fun sendMessage(message: String) {

    }
}

private fun mainState(isLoading: Boolean = false, gameState: GameState? = null) =
    MainState(isLoading = isLoading, gameState = gameState)

val emptyBoard = listOf(
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
    Cell(),
)
