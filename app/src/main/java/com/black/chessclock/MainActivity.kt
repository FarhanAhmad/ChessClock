package com.black.chessclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.black.chessclock.ui.theme.ChessClockTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ClockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val startGameLamda = remember {
                {
                    viewModel.onViewClicked(ClockViewModel.UiEvent.START_RESET)
                }
            }
            val increaseGameTimeLamda = remember {
                {
                    viewModel.onViewClicked(ClockViewModel.UiEvent.INCREASE_TIME)
                }
            }

            val decreaseGameTimeLamda = remember {
                {
                    viewModel.onViewClicked(ClockViewModel.UiEvent.DECREASE_TIME)
                }
            }

            val clockAClickLamda = remember {
                {
                    viewModel.onViewClicked(ClockViewModel.UiEvent.CLOCK_A)
                }
            }

            val clockBClickLamda = remember {
                {
                    viewModel.onViewClicked(ClockViewModel.UiEvent.CLOCK_B)
                }
            }

            ChessClockTheme {
                val uiState = viewModel.uiState.collectAsState(initial = ClockState())
                ClockView(
                    uiState = uiState.value,
                    startGame = startGameLamda,
                    clockAClick = clockAClickLamda,
                    clockBClick = clockBClickLamda,
                    increaseGameTime = increaseGameTimeLamda,
                    decreaseGameTime = decreaseGameTimeLamda,
                    gameActionText = if (uiState.value.gameInProgress) {
                        "reset"
                    } else {
                        "start"
                    }
                )
            }
        }
    }
}