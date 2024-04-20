package com.example.hikingtrails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) { // activity runs on small screen
        super.onCreate(savedInstanceState) // call the superclass onCreate
        setContentView(R.layout.trail_activity_details) // set the content view

        val trailsId = intent?.getIntExtra(TrailDetailsFragment.ARG_TRAIL_ID, 0) ?: 0 // get the trailsId from the intent
        println("DetailActivity onCreate: $trailsId")
        (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? TrailDetailsFragment)?.apply { // get the fragment
            arguments = Bundle().apply { // set the arguments
                trailId = trailsId // set the trailId
            }
        }
    }
}