package com.example.hikingtrails

import Trail
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class TrailDetailsFragment : Fragment() {
    companion object {
        const val ARG_TRAIL_ID = "trail_id"
    }

    var trailId: Int = 0
    private var trail: Trail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the trailId from the arguments if available
        arguments?.let {
            trailId = it.getInt(ARG_TRAIL_ID)
        }

        val timerFragment = TimerFragment.newInstance(trailId)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.timer, timerFragment)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("TrailDetailsFragment onCreateView: $trailId")
        trail = DatabaseHandler(requireContext()).readData()[trailId]
        return inflater.inflate(R.layout.trail_details, container, false)
    }

    override fun onStart() {
        println("TrailDetailsFragment onStart: $trail")
        super.onStart()
        view?.let {
            with(trail!!) {
                it.findViewById<ImageView>(R.id.image).setImageBitmap(image)
                it.findViewById<TextView>(R.id.textTitle).text = name
                it.findViewById<TextView>(R.id.list_details).text = description
            }
        }
    }
}
