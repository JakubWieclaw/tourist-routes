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
fun Stopwatch(trailId: Int, context: Context) {
    var elapsedTime by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    val handler = remember { Handler(Looper.getMainLooper()) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            handler.post(object : Runnable {
                override fun run() {
                    elapsedTime++
                    handler.postDelayed(this, 1000)
                }
            })
        } else {
            handler.removeCallbacksAndMessages(null)
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
                    text = String.format("%02d:%02d:%02d", elapsedTime / 3600, (elapsedTime % 3600) / 60, elapsedTime % 60),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { isRunning = !isRunning }) {
                        Text(if (isRunning) "Pause" else "Start")
                    }
                    Button(onClick = {
                        saveTime()
                        elapsedTime = 0
                        isRunning = false
                    }) {
                        Text("Save & Reset")
                    }
                }
            }
        }
    }
}

