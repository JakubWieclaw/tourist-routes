package com.example.hikingtrails.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.hikingtrails.DatabaseHandler
import com.example.hikingtrails.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopwatchViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private var timerJob: Job? = null

    val elapsedTime = savedStateHandle.getLiveData("elapsed_time", 0)
    val isRunning = savedStateHandle.getLiveData("is_running", false)

    fun startTimer() {
        if (timerJob == null) {
            timerJob = viewModelScope.launch {
                while (true) {
                    delay(1000)
                    elapsedTime.value = (elapsedTime.value ?: 0) + 1
                }
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun resetTimer() {
        stopTimer()
        elapsedTime.value = 0
    }

    fun toggleTimer() {
        if (isRunning.value == true) {
            stopTimer()
        } else {
            startTimer()
        }
        isRunning.value = !(isRunning.value ?: false)
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}

class StopwatchViewModelFactory(
    owner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return StopwatchViewModel(handle) as T
    }
}


@Composable
fun Stopwatch(trailId: Int, context: Context, key: String) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    val savedStateRegistryOwner = viewModelStoreOwner as SavedStateRegistryOwner

    val viewModel: StopwatchViewModel = viewModel(
        factory = StopwatchViewModelFactory(savedStateRegistryOwner),
        key = key
    )

    val elapsedTime by viewModel.elapsedTime.observeAsState(0)
    val isRunning by viewModel.isRunning.observeAsState(false)

    LaunchedEffect(isRunning, key) {
        if (isRunning) {
            viewModel.startTimer()
        } else {
            viewModel.stopTimer()
        }
    }

    fun saveTime() {
        val dbHandler = DatabaseHandler(context)
        dbHandler.insertTimeMeasurement(elapsedTime, trailId)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Stopwatch",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = String.format(
                        "%02d:%02d:%02d",
                        elapsedTime / 3600,
                        (elapsedTime % 3600) / 60,
                        elapsedTime % 60
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { viewModel.toggleTimer() }) {
                        Image(
                            painter = painterResource(id = if (isRunning) R.drawable.pause else R.drawable.start),
                            contentDescription = if (isRunning) "Pause" else "Start",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Button(onClick = {
                        saveTime()
                        viewModel.resetTimer()
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.save),
                            contentDescription = "Save & Reset",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
}
