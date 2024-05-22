import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.hikingtrails.ui.CameraFab
import com.example.hikingtrails.ui.Stopwatch
import com.example.hikingtrails.ui.TimeMeasurements

@Composable
fun TrailDetails(trail: Trail, context: android.content.Context) {
    val context = LocalContext.current
    val text = trail.name

    Box(modifier = Modifier.fillMaxSize()) {
        Card(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Details of $text",
                    fontSize = 24.sp,
                )
                // Show the trail photo
                trail.image?.let { imageUrl ->
                    val painter = rememberImagePainter(data = imageUrl)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 16.dp)
                    )
                }
                Spacer(Modifier.size(16.dp))
                Text(
                    text = trail.description
                )
                Spacer(Modifier.size(16.dp))

                // Show time measurements
                TimeMeasurements(trailId = trail.id, context = context)

                // Add the Stopwatch composable and pass the trailId
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    val stopwatchKey = "stopwatch_${trail.id}"
                    val stopwatchState = remember(stopwatchKey) { mutableStateOf(0) }
                    val isRunningState = remember(stopwatchKey) { mutableStateOf(false) }
                    Stopwatch(trailId = trail.id, context = context, elapsedTime = stopwatchState, isRunning = isRunningState)
                }
            }
        }
        // Place the CameraFab at the top right
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
            CameraFab(context = context, modifier = Modifier.padding(16.dp))
        }
    }
}
