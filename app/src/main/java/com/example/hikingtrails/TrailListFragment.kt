package com.example.hikingtrails

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.ListFragment

class TrailListFragment : ListFragment() {
    private var trailSelectListener: OnTrailSelectedListener? = null // listener for trail selection

    interface OnTrailSelectedListener {
        fun onTrailSelected(trailId: Int) // called when a trail is selected
        abstract fun displayOnTablet(trailId: Int)
    }

    override fun onAttach(context: Context) { // called when the fragment is attached to the activity
        println("TrailListFragment onAttach")
        super.onAttach(context) // call super's onAttach
        trailSelectListener = context as? OnTrailSelectedListener // listen for trail selection
    }

    override fun onCreateView( // called when the fragment is created
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("TrailListFragment onCreateView")
        // delete database
        DatabaseHandler(requireContext()).deleteData()
        DatabaseHandler(requireContext()).insertExampleData(
            requireContext()
        )

        val trails = DatabaseHandler(requireContext()).readData() // get the trails from the database
//        DatabaseHandler(requireContext()).insertExampleData()
        val trailNames = trails.map { it.name }.toTypedArray() // get the trail names
        val listAdapter = ArrayAdapter(inflater.context, R.layout.simple_list_item_1, trailNames) // create an adapter for the list view
        setListAdapter(listAdapter) // set the adapter
        return super.onCreateView(inflater, container, savedInstanceState) // call super's onCreateView
    }

    @Deprecated("This method is deprecated.")
    override fun onActivityCreated(savedInstanceState: Bundle?) { // called when the activity is created
        println("TrailListFragment onActivityCreated")
        super.onActivityCreated(savedInstanceState) // call super's onActivityCreated
        listView.setOnItemClickListener { _, _, position, _ -> // set the item click listener
            println("TrailListFragment onActivityCreated: $position")
            trailSelectListener?.onTrailSelected(position) // call the listener's onTrailSelected method
        }
    }
}