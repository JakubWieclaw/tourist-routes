package com.example.hikingtrails

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment

class TrailListFragment : ListFragment() {
    private var trailSelectListener: OnTrailSelectedListener? = null

    interface OnTrailSelectedListener {
        fun onTrailSelected(trailId: Int)
        abstract fun displayOnTablet(trailId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        trailSelectListener = context as? OnTrailSelectedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val trails = DatabaseHandler(requireContext()).readData()
//        DatabaseHandler(requireContext()).insertExampleData()
        println(trails)
        val trailNames = trails.map { it.name }.toTypedArray()
        val listAdapter = ArrayAdapter(inflater.context, R.layout.simple_list_item_1, trailNames)
        setListAdapter(listAdapter)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @Deprecated("This method is deprecated.")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listView.setOnItemClickListener { _, _, position, _ ->
            println("TrailListFragment onActivityCreated: $position")
            trailSelectListener?.onTrailSelected(position)
        }
    }
}