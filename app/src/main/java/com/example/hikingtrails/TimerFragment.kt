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

class TimerFragment : Fragment() {
    private var elapsedTime: Int = 0
    private var isRunning: Boolean = false
    private var wasPreviouslyRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            elapsedTime = it.getInt("elapsedTime")
            isRunning = it.getBoolean("isRunning")
            wasPreviouslyRunning = it.getBoolean("wasPreviouslyRunning")
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
        view.findViewById<Button>(R.id.button).apply { setOnClickListener { isRunning = true } }
        view.findViewById<Button>(R.id.button2).apply { setOnClickListener { isRunning = false } }
        view.findViewById<Button>(R.id.button3).apply { setOnClickListener { isRunning=false; elapsedTime=0 } }
    }

//    private fun onStartPressed() {
//        isRunning = true
//    }

  /*  private fun onStopPressed() {
        isRunning = false
    }
*/
//    private fun onResetPressed() {
//        isRunning = false
//        elapsedTime = 0
//    }

    private fun startStopwatch(view: View) {
        val timeDisplay: TextView = view.findViewById(R.id.timer_text)
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                val hours = elapsedTime / 3600
                val minutes = (elapsedTime % 3600) / 60
                val seconds = elapsedTime % 60
                val time = String.format("%d:%02d:%02d", hours, minutes, seconds)
                timeDisplay.text = time
                if (isRunning) {
                    elapsedTime++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        wasPreviouslyRunning = isRunning
        isRunning = false
    }

    override fun onResume() {
        super.onResume()
        isRunning = wasPreviouslyRunning
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("elapsedTime", elapsedTime)
        outState.putBoolean("isRunning", isRunning)
        outState.putBoolean("wasPreviouslyRunning", wasPreviouslyRunning)
    }
}
