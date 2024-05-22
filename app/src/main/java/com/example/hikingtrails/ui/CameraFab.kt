package com.example.hikingtrails.ui

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.hikingtrails.R

@Composable
fun CameraFab(context: Context, modifier: Modifier = Modifier ) {
    val openCameraIntent = remember { mutableStateOf(Intent(MediaStore.ACTION_IMAGE_CAPTURE)) }
    val fabClick = remember { mutableStateOf(false) }

    LaunchedEffect(fabClick.value) {
        if (fabClick.value) {
            context.startActivity(openCameraIntent.value)
            fabClick.value = false
        }
    }

    FloatingActionButton(
        onClick = { fabClick.value = true },
        modifier = modifier
    ) {
        Text("Take a photo")
    }
}