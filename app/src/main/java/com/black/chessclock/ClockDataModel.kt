package com.black.chessclock

data class ClockDataModel(
    val minutes: Int,
    val seconds: Int,
    val centiSeconds: Int,
)

data class ClockState(
    val gameInProgress: Boolean = false,
    val clockARunning: Boolean = false,
    val clockBRunning: Boolean = false,
    val clockATime: String = "00:00:00",
    val clockATimeLeft: Long = 0L,
    val clockBTime: String = "00:00:00",
    val clockBTimeLeft: Long = 0L,
)