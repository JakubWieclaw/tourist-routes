package com.example.hikingtrails

import androidx.compose.runtime.saveable.Saver

class TrailIdx(val idx: Int) {
    companion object {
        val Saver: Saver<TrailIdx?, Int> = Saver(
            { it?.idx },
            ::TrailIdx,
        )
    }
}
