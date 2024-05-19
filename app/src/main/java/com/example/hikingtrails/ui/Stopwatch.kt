package com.example.hikingtrails.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hikingtrails.DatabaseHandler

@Composable
fun Stopwatch(trailId: Int, context: Context, elapsedTime: MutableState<Int>, isRunning: MutableState<Boolean>) {
    val handler = remember { Handler(Looper.getMainLooper()) }

    LaunchedEffect(isRunning.value) {
        if (isRunning.value) {
            handler.post(object : Runnable {
                override fun run() {
                    elapsedTime.value++
                    handler.postDelayed(this, 1000)
                }
            })
        } else {
            handler.removeCallbacksAndMessages(null)
        }
    }

    fun saveTime() {
        val dbHandler = DatabaseHandler(context)
        dbHandler.insertTimeMeasurement(elapsedTime.value, trailId)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            // Empty space
        }
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
                        elapsedTime.value / 3600,
                        (elapsedTime.value % 3600) / 60,
                        elapsedTime.value % 60
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { isRunning.value = !isRunning.value }) {
                        Text(if (isRunning.value) "Pause" else "Start")
                    }
                    Button(onClick = {
                        saveTime()
                        elapsedTime.value = 0
                        isRunning.value = false
                    }) {
                        Text("Save & Reset")
                    }
                }
            }
        }
    }
}

