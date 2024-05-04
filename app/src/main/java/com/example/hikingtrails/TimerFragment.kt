package com.example.hikingtrails

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

// https://www.geeksforgeeks.org/how-to-create-a-stopwatch-app-using-android-studio/ - inspiration
class TimerFragment : Fragment() {
    private var elapsedTime: Int = 0
    private var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            elapsedTime = it.getInt("elapsedTime")
            isRunning = it.getBoolean("isRunning")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_stopwatch, container, false)
        setupButtons(rootView)
        startStopwatch(rootView)
        return rootView
    }

    private fun setupButtons(view: View) {
        view.findViewById<Button>(R.id.button).apply { setOnClickListener { isRunning = !isRunning; R.id.button.apply { text =
            if (isRunning) "Stop" else "Start" } } }
        view.findViewById<Button>(R.id.button3).apply { setOnClickListener {  elapsedTime=0 } }
    }
    private fun startStopwatch(view: View) {
        val timeDisplay: TextView = view.findViewById(R.id.timer_text)
        val handler = Handler(Looper.getMainLooper())

        handler.post(object : Runnable {
            override fun run() {
                val hours = elapsedTime / 3600
                val minutes = (elapsedTime % 3600) / 60
                val seconds = elapsedTime % 60
                val strHours = if (hours < 10) "0$hours" else hours.toString()
                val strMinutes = if (minutes < 10) "0$minutes" else minutes.toString()
                val strSeconds = if (seconds < 10) "0$seconds" else seconds.toString()
                val time = "$strHours:$strMinutes:$strSeconds"
                timeDisplay.text = time
                if (isRunning) {
                    elapsedTime++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("elapsedTime", elapsedTime)
        outState.putBoolean("isRunning", isRunning)
    }
}
