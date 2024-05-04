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
        val timerFragment = TimerFragment()
        childFragmentManager.beginTransaction().apply {
            replace(R.id.timer, timerFragment) // Utilize `replace` to manage fragments effectively
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
        trail = DatabaseHandler(requireContext()).readData()[trailId] // trail is assigned the value of the trail with the id of trailId
        return inflater.inflate(R.layout.trail_details, container, false) // infate method is used to convert XML layout file into a view
    }

    override fun onStart() {
        println("TrailDetailsFragment onStart: $trail")
        super.onStart()
        view?.let {
            with(trail!!) {
                it.findViewById<ImageView>(R.id.image).setImageBitmap(image)
                it.findViewById<TextView>(com.example.hikingtrails.R.id.textTitle).text = name
                it.findViewById<TextView>(com.example.hikingtrails.R.id.list_details).text = description
            }
        }
    }

}