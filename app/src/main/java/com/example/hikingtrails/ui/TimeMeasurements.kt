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
fun TimeMeasurements(trailName: String, trailId: Int, context: Context) {
    val dbHandler = DatabaseHandler(context)
    val timeMeasurements = remember { mutableStateListOf<Pair<Int, Long>>() }

    LaunchedEffect(trailId) {
        timeMeasurements.clear()
        timeMeasurements.addAll(dbHandler.getTimeMeasurementsForTrailWithTimestamp(trailId))
    }

    Text(text = "Time Measurements for $trailName", fontSize = 20.sp, modifier = Modifier.padding(horizontal = 16.dp))

    if (timeMeasurements.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            Text(
//                text = "Time Measurements:",
//                fontSize = 20.sp,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
            timeMeasurements.forEachIndexed { index, measurement ->
                Text(
                    text = "${index + 1}. ${String.format("%02d:%02d:%02d", measurement.first / 3600, (measurement.first % 3600) / 60, measurement.first % 60)}",
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    } else {
        Text(
            text = "No time measurements recorded yet.",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
