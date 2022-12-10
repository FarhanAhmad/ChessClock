package com.black.chessclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.black.chessclock.ui.theme.ChessClockTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ClockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ChessClockTheme {
                val uiState = viewModel.uiState.collectAsState(initial = ClockState())
                ClockView(
                    uiState = uiState.value,
                    startGame = { viewModel.onViewClicked(ClockViewModel.UiEvent.START_RESET) },
                    clockAClick = { viewModel.onViewClicked(ClockViewModel.UiEvent.CLOCK_A) },
                    clockBClick = { viewModel.onViewClicked(ClockViewModel.UiEvent.CLOCK_B) },
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChessClockTheme {
        Greeting("Android")
    }
}