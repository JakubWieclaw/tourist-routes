package com.example.hikingtrails

import Trail
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(), TrailListFragment.OnTrailSelectedListener {

    override fun onTrailSelected(trailId: Int) {
        println("MainActivity onTrailSelected: $trailId")
        findViewById<View>(R.id.trail_details)?.let {
            displayOnTablet(trailId) // displays the trail details on the tablet
        } ?: run {
            Intent(this, DetailActivity::class.java).apply {
                putExtra(TrailDetailsFragment.ARG_TRAIL_ID, trailId) // needed to display the trail details on small screens
                startActivity(this) // starts the DetailActivity
            }
        }
    }

    override fun displayOnTablet(trailId: Int) {
        println("MainActivity displayOnTablet: $trailId")
        val details = TrailDetailsFragment().apply {
            this.trailId = trailId // sets the trailId property of the TrailDetailsFragment
        }
        supportFragmentManager.commit {
            replace(R.id.trail_details, details) // R.id.trail_details is the id of the FrameLayout in activity_main.xml
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) // animation when fragment is opened
            addToBackStack(null) // allows user to go back to the previous fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) { // bundle is a collection of key-value pairs
        super.onCreate(savedInstanceState) // calls the superclass onCreate method
        println("MainActivity onCreate")
        try{
            setContentView(R.layout.activity_main) // sets the content view to activity_main.xml
        } catch (e: Exception) {
            Log.e("MainActivity", "Error setting content view", e)
        }
    }

}