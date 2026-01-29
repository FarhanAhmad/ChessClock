package com.black.chessclock

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito.verify

class ClockViewModelTest {

    private lateinit var viewModel: ClockViewModel

    @Before
    fun setUp() {
        viewModel = ClockViewModel()
    }

    @Test
    fun testStartOrReset() = runTest {

        viewModel.onViewClicked(ClockViewModel.UiEvent.START_RESET)

        val state = viewModel.uiState.first()
        assertTrue(state.gameInProgress)
        assertTrue(state.clockBRunning)
        assertFalse(state.clockARunning)
    }
}