import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.hikingtrails.ui.CameraFab
import com.example.hikingtrails.ui.Stopwatch
import com.example.hikingtrails.ui.TimeMeasurements
import kotlin.math.min

@Composable
fun TrailDetails(trail: Trail, context: android.content.Context) {
    val scrollState = rememberScrollState()
    val imageHeight = 200.dp
    val collapsedImageHeight = imageHeight
    val maxOffset = with(LocalDensity.current) { (imageHeight - collapsedImageHeight).toPx() }

    Box(modifier = Modifier.fillMaxSize()) {
        // Collapsed Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(collapsedImageHeight)
                .graphicsLayer {
                    val offset = min(scrollState.value.toFloat(), maxOffset)
                    translationY = -offset
                    alpha = offset / maxOffset
                }
        ) {
            trail.image?.let { imageUrl ->
                val painter = rememberImagePainter(data = imageUrl)
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight)
                        .graphicsLayer {
                            val offset = min(scrollState.value.toFloat(), maxOffset)
                            translationY = -offset / 2
                            alpha = 1 - (offset / maxOffset)
                        }
                ) {
                    trail.image?.let { imageUrl ->
                        val painter = rememberImagePainter(data = imageUrl)
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(imageHeight)
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Details of ${trail.name}",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = trail.description,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Show time measurements
                TimeMeasurements(trailName = trail.name, trailId = trail.id, context = context)

                // Add the Stopwatch composable and pass the trailId
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                    val stopwatchKey = "stopwatch_${trail.id}"
                    val stopwatchState = remember(stopwatchKey) { mutableStateOf(0) }
                    val isRunningState = remember(stopwatchKey) { mutableStateOf(false) }
                    Stopwatch(
                        trailId = trail.id,
                        context = context,
                        elapsedTime = stopwatchState,
                        isRunning = isRunningState
                    )
                }
            }
        }

        // FloatingActionButton at the top right
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
            CameraFab(context = context, modifier = Modifier.padding(16.dp))
        }

        // Collapsed Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(collapsedImageHeight)
                .graphicsLayer {
                    val offset = min(scrollState.value.toFloat(), maxOffset)
                    translationY = -offset
                    alpha = offset / maxOffset
                }
        ) {
            trail.image?.let { imageUrl ->
                val painter = rememberImagePainter(data = imageUrl)
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
