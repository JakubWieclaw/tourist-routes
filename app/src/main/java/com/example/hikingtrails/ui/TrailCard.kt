package com.example.hikingtrails.ui

import Trail
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hikingtrails.TrailIdx

@Composable
fun TrailCard(trail: Trail, onItemClick: (TrailIdx) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(TrailIdx(trail.id - 1)) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            trail.image?.let { image ->
                Box(
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape) // Clip the Box to a circle
                ) {
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop, // Crop the image to fit within the Box
                        modifier = Modifier.fillMaxSize() // Fill the entire Box
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = trail.name,
                textAlign = TextAlign.Center
            )
        }
    }
}
