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
        println("onTrailSelected: $trailId")
        val detailContainer = findViewById<View>(R.id.trail_details)
        detailContainer?.let {
            displayOnTablet(trailId)
        } ?: run {
            Intent(this, DetailActivity::class.java).apply {
                putExtra(TrailDetailsFragment.ARG_TRAIL_ID, trailId)
                startActivity(this)
            }
        }
    }

    override fun displayOnTablet(trailId: Int) {
        val details = TrailDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(TrailDetailsFragment.ARG_TRAIL_ID, trailId)
            }
        }
        details.trailId = trailId
        supportFragmentManager.commit {
            replace(R.id.trail_details, details)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            addToBackStack(null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try{
            setContentView(R.layout.activity_main)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error setting content view", e)
        }
        val fragmentContainer = findViewById<View>(R.id.trail_details)
        if (fragmentContainer != null && DatabaseHandler(this).readData().isNotEmpty()) {
            displayOnTablet(0)
        }
    }

}