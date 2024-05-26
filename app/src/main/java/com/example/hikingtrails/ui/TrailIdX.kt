package com.example.hikingtrails

import androidx.compose.runtime.saveable.Saver

data class TrailIdx(val idx: Int) {
    companion object {
        val Saver: Saver<TrailIdx, *> = Saver(
            save = { it.idx },
            restore = { TrailIdx(it) }
        )
        val None = TrailIdx(-1)
    }
}
