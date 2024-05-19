package com.example.hikingtrails.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hikingtrails.DatabaseHandler

@Composable
fun TimeMeasurements(trailId: Int, context: Context) {
    val dbHandler = DatabaseHandler(context)
    val timeMeasurements = remember { mutableStateListOf<Int>() }

    LaunchedEffect(trailId) {
        timeMeasurements.clear()
        timeMeasurements.addAll(dbHandler.getTimeMeasurementsForTrail(trailId))
    }

    if (timeMeasurements.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Time Measurements:",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            timeMeasurements.forEach { measurement ->
                Text(
                    text = String.format("%02d:%02d:%02d", measurement / 3600, (measurement % 3600) / 60, measurement % 60),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    } else {
        Text(
            text = "No time measurements recorded yet.",
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}
