package com.example.hikingtrails.ui

import Trail
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hikingtrails.TrailIdx

@Composable
fun TrailsToCards(
    trails: List<Trail>,
    onItemClick: (TrailIdx) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(trails.size) { idx ->
            TrailCard(trails[idx], onItemClick)
        }
    }
}

