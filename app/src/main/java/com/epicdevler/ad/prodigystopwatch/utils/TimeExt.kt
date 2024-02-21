package com.epicdevler.ad.prodigystopwatch.utils
fun Long.toTimerValue(point: TimerPoint): String{

    val value = when(point){
        TimerPoint.MILLI -> {
            this % 1000 / 10
        }
        TimerPoint.SEC -> {
            this % 60000 / 1000
        }
        TimerPoint.MIN -> {
            this % 360000 / 60000
        }
        TimerPoint.HH -> {
            this / 3600000
        }
    }
    return value.toString().padStart(2, '0')
}

enum class TimerPoint{
    MILLI, SEC, MIN, HH
}

