package com.example.hikingtrails.ui

import Trail
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hikingtrails.TrailIdx

@Composable
fun TrailsToCards(
    trails: List<Trail>,
    onItemClick: (TrailIdx) -> Unit,
) {
    Card {
        LazyColumn {
            trails.forEachIndexed { idx, trail ->
                item {
                    ListItem(
                        modifier = Modifier
                            .background(Color.Magenta)
                            .clickable {
                                onItemClick(TrailIdx(idx))
                            },
                        headlineContent = {
                            Text(
                                text = trail.name,
                            )
                        },
                    )
                }
            }
        }
    }
}
