package com.timgt.battleship

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `test`() = runTest {
        val viewModel = MainViewModel()

        viewModel.findOpponentClicked()

        assertEquals(mainState(isLoading = true), viewModel.uiState.value)
    }
}

private fun mainState(isLoading: Boolean = false) = MainState(isLoading = isLoading)

class MainDispatcherRule(private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
