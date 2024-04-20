package com.example.hikingtrails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trail_activity_details)

        val trailsId = intent?.getIntExtra(TrailDetailsFragment.ARG_TRAIL_ID, 0) ?: 0
        println("DetailActivity onCreate: $trailsId")
        (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? TrailDetailsFragment)?.apply {

            arguments = Bundle().apply {
                putInt(TrailDetailsFragment.ARG_TRAIL_ID, trailsId)
                trailId = trailsId
            }
        }
    }
}