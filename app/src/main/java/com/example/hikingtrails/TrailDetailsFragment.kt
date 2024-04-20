package com.example.hikingtrails

import Trail
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class TrailDetailsFragment : Fragment() {
    companion object {
        const val ARG_TRAIL_ID = "trail_id"
    }

    var trailId: Int = 0
    private var trail: Trail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null)
            trailId = savedInstanceState.getInt(ARG_TRAIL_ID)
//        val trailsId = intent.getIntExtra(TrailDetailsFragment.ARG_TRAIL_ID, 0) ?: 0

        println("TrailDetailsFragment onCreate: $trailId")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        trailId = savedInstanceState?.getInt(ARG_TRAIL_ID) ?: 0
//        println("TrailDetailsFragment onCreateView: $trailId")
        trail = DatabaseHandler(requireContext()).readData()[trailId]
        return inflater.inflate(R.layout.trail_details, container, false)
    }

    override fun onStart() {
        super.onStart()
        view?.let {
            with(trail!!) {
                it.findViewById<TextView>(com.example.hikingtrails.R.id.textTitle).text = name
                it.findViewById<TextView>(com.example.hikingtrails.R.id.list_details).apply {
                    text = description
                    movementMethod = android.text.method.LinkMovementMethod.getInstance()
                }
            }
        }
    }

}