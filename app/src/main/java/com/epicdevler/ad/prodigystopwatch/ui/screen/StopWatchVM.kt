package com.epicdevler.ad.prodigystopwatch.ui.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epicdevler.ad.prodigystopwatch.ui.screen.StopWatchVM.Event.*
import com.epicdevler.ad.prodigystopwatch.utils.TimerPoint.*
import com.epicdevler.ad.prodigystopwatch.utils.toTimerValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopWatchVM : ViewModel() {

    val uiState: MutableState<UiState> = mutableStateOf(UiState())
    private var elapsedMillis = 0L
    private var job: Job? = null

    private fun resetWatch() {
        elapsedMillis = 0
        uiState.value = uiState.value.reset()
        if (job?.isActive == true) job?.cancel()
    }

    private fun pauseWatch() {
        job?.cancel()
        uiState.value = uiState.value.copy(isRunning = false)
        job = null
    }

    private suspend fun startWatch() {
        if (job?.isActive == true)
            job?.cancelAndJoin()
        job = CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            uiState.value = uiState.value.copy(isRunning = true)
            while (uiState.value.isRunning){
                delay(1)
                elapsedMillis += 10
                if (elapsedMillis % 1000 >= 900) {
                    elapsedMillis += 1000 - (elapsedMillis % 1000)
                }
                uiState.value = uiState.value.copy(
                    timer = UiState.Timer(
                        milli = elapsedMillis.toTimerValue(MILLI),
                        sec = elapsedMillis.toTimerValue(SEC),
                        min = elapsedMillis.toTimerValue(MIN),
                        hh = elapsedMillis.toTimerValue(HH),
                    )
                )
            }
        }
    }


    fun onEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                START -> startWatch()
                PAUSE -> pauseWatch()
                RESET -> resetWatch()
            }
        }
    }

    fun toggleStart() {
        if (uiState.value.isRunning)
            onEvent(PAUSE)
        else onEvent(START)
    }

    interface Event {
        object START : Event
        object PAUSE : Event
        object RESET : Event
    }

    data class UiState(
        var timer: Timer = Timer(),
        val isRunning: Boolean = false
    ) {
        data class Timer(
            val hh: String = "00",
            val min: String = "00",
            val sec: String = "00",
            val milli: String = "00",
        )


        fun reset(): UiState = UiState()
    }

}