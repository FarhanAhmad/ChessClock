package com.black.chessclock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.black.chessclock.ui.theme.ChessClockTheme

const val TAG = "ClockView"

@Composable
fun ClockBoardView(
    uiState: ClockState,
    clockAClick: () -> Unit,
    clockBClick: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(Color.DarkGray)
                .weight(1f)
                .fillMaxWidth()
                .clickable(onClick = clockAClick)
        ) {
            Text(
                text = uiState.clockATime,
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer {
                        rotationX = 180f
                        rotationY = 180f
                    },
                fontSize = 30.sp,
                color = if (uiState.clockATimeLeft == 0L) Color.Red else Color.LightGray,
            )
        }
        Divider(
            color = Color.Gray,
            thickness = 2.dp,
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable(onClick = clockBClick)
        ) {
            Text(
                text = uiState.clockBTime,
                modifier = Modifier
                    .align(Alignment.Center),
                fontSize = 30.sp,
                color = if (uiState.clockBTimeLeft == 0L) Color.Red else Color.Black,
            )
        }

    }
}

@Composable
fun ClockView(
    uiState: ClockState,
    startGame: () -> Unit,
    clockAClick: () -> Unit,
    clockBClick: () -> Unit,
    increaseGameTime: () -> Unit,
    decreaseGameTime: () -> Unit,
    gameActionText: String,
) {

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        ClockBoardView(
            uiState = uiState,
            clockAClick = clockAClick,
            clockBClick = clockBClick,
        )

        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = startGame
        ) {
            Text(text = gameActionText)
        }

        if (!uiState.gameInProgress)
            GameTime(
                modifier = Modifier.align(Alignment.BottomCenter),
                increaseGameTime = increaseGameTime,
                decreaseGameTime = decreaseGameTime,
            )
    }
}

@Composable
fun GameTime(
    modifier: Modifier,
    increaseGameTime: () -> Unit,
    decreaseGameTime: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = decreaseGameTime,
            modifier = Modifier
                .size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Reduce time"
            )
        }
        Text(
            text = "Set game time",
            modifier = Modifier.padding(8.dp),
            fontSize = 16.sp,
        )
        IconButton(
            onClick = increaseGameTime,
            modifier = Modifier
                .size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Increase time"
            )
        }

    }
}

@Preview
@Composable
fun ClockPreview() {
    ChessClockTheme {
        ClockView(
            uiState = ClockState(
                gameInProgress = false,
                clockATime = "04:30:21",
                clockBTime = "04:30:22",
            ),
            startGame = {},
            clockAClick = {},
            clockBClick = {},
            increaseGameTime = {},
            decreaseGameTime = {},
            "Start",
        )
    }
}