package com.epicdevler.ad.prodigystopwatch.ui.screen

import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epicdevler.ad.prodigystopwatch.ui.screen.StopWatchVM.Event.RESET


@Composable
fun StopWatchScreen() {
    val vm: StopWatchVM = viewModel()

    val uiState = vm.uiState.value

    val isRunning = uiState.isRunning

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "Prodigy Stop Watch",
            style = typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
        TimerValues(
            vm.uiState.value.timer
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(

                onClick = {
                    vm.toggleStart()
                }

            ) {
                val icon = if (!isRunning) Icons.Rounded.PlayArrow else Icons.Rounded.Pause
                Icon(imageVector = icon, contentDescription = icon.name)
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                onClick = { vm.onEvent(RESET) }
            ) {
                val icon = Icons.Rounded.Refresh
                Icon(imageVector = icon, contentDescription = icon.name)
            }
        }
    }
}

@Composable
fun ColumnScope.TimerValues(timer: StopWatchVM.UiState.Timer) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Time(
                value = timer.hh
            )

            Text(
                text = ":",
                style = typography.displaySmall,
                modifier = Modifier.padding(horizontal = 10.dp)
            )


            Time(
                value = timer.min
            )

            Text(
                text = ":",
                style = typography.displaySmall,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Time(
                value = timer.sec
            )

        }
        Time(
            value = timer.milli,
            style = typography.bodyLarge
        )

    }
}

@Composable
fun Time(value: String, style: TextStyle = typography.displayMedium) {

    Text(
        text = value,
        style = style
    )
}