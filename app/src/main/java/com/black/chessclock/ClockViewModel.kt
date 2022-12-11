package com.black.chessclock

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ClockViewModel : ViewModel() {

    private val TAG = "ClockViewModel"
    private val _uiState = MutableStateFlow(ClockState())
    val uiState = _uiState.asSharedFlow()

    var gameJob: Job? = null

    private var gameTime = 2 * 60 * 1000L

    private val MAX_GAME_TIME = 10 * 60 * 1000L
    private val MIN_GAME_TIME = 1 * 60 * 1000L

    private fun formatTime(remainingTime: Long): String {
        val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime).mod(60)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime)
        val centiSeconds = (remainingTime - minutes.times(seconds).times(1000)).div(100).mod(10)

        return String.format(
            "%02d:%02d:%02d",
            minutes,
            seconds,
            centiSeconds
        )
    }

    fun onViewClicked(event: UiEvent) {
        when (event) {
            UiEvent.START_RESET -> {
                if (_uiState.value.gameInProgress) {
                    resetClock()
                } else {
                    startGame()
                }
            }
            UiEvent.CLOCK_A -> {
                _uiState.update {
                    it.copy(
                        clockARunning = false,
                        clockBRunning = true,
                    )
                }
            }
            UiEvent.CLOCK_B -> {
                _uiState.update {
                    it.copy(
                        clockARunning = true,
                        clockBRunning = false,
                    )
                }
            }
            UiEvent.INCREASE_TIME -> {
                if (gameTime < MAX_GAME_TIME) {
                    gameTime += 60000
                    resetClock()
                }
            }
            UiEvent.DECREASE_TIME -> {
                if (gameTime > MIN_GAME_TIME) {
                    gameTime -= 60000
                    resetClock()
                }
            }
        }
    }

    private fun resetClock() {
        gameJob?.cancel()
        _uiState.update {
            it.copy(
                gameInProgress = false,
                clockATimeLeft = gameTime,
                clockBTimeLeft = gameTime,
                clockARunning = false,
                clockBRunning = false,
                clockATime = formatTime(gameTime),
                clockBTime = formatTime(gameTime),
            )
        }
    }

    private fun startGame() {

        _uiState.update {
            it.copy(
                gameInProgress = true,
                clockATimeLeft = gameTime,
                clockBTimeLeft = gameTime,
                clockARunning = false,
                clockBRunning = true,
                clockATime = formatTime(gameTime),
                clockBTime = formatTime(gameTime),
            )
        }

        val delayInMs = 100L
        gameJob = viewModelScope.launch {
            while (isActive) {
                delay(delayInMs)

                Log.i(TAG, "Emitting state")
                if (_uiState.value.clockARunning) {
                    val timeLeft = _uiState.value.clockATimeLeft - delayInMs
                    if (timeLeft <= 0) {
                        endGame(true)
                    } else {
                        _uiState.update {
                            it.copy(
                                clockATimeLeft = timeLeft,
                                clockATime = formatTime(timeLeft),
                            )
                        }
                    }
                } else {
                    val timeLeft = _uiState.value.clockBTimeLeft - delayInMs
                    if (timeLeft <= 0) {
                        endGame(false)
                    } else {
                        _uiState.update {
                            it.copy(
                                clockBTimeLeft = timeLeft,
                                clockBTime = formatTime(timeLeft),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun endGame(isAEnded: Boolean) {
        gameJob?.cancel()

        _uiState.update {
            if (isAEnded) {
                it.copy(
                    gameInProgress = false,
                    clockATimeLeft = 0,
                    clockATime = formatTime(0),
                )
            } else {
                it.copy(
                    gameInProgress = false,
                    clockBTimeLeft = 0,
                    clockBTime = formatTime(0),
                )
            }

        }
    }

    sealed interface UiEvent {
        object START_RESET : UiEvent
        object CLOCK_A : UiEvent
        object CLOCK_B : UiEvent
        object INCREASE_TIME : UiEvent
        object DECREASE_TIME : UiEvent
    }
}