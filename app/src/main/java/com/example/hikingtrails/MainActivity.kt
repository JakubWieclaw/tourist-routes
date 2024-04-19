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
        val trailNames = trails.map { it.name }.toTypedArray()
        val listAdapter = ArrayAdapter(inflater.context, android.R.layout.simple_list_item_1, trailNames)
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
                it.findViewById<TextView>(R.id.textTitle).text = name
                it.findViewById<TextView>(R.id.list_details).apply {
                    text = description
                    movementMethod = LinkMovementMethod.getInstance()
                }
            }
        }
    }

}

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

class MainActivity : AppCompatActivity(), TrailListFragment.OnTrailSelectedListener {

    override fun onTrailSelected(trailId: Int) {
        println("onTrailSelected: $trailId")
//        val detailContainer = findViewById<View>(R.id.TrailsList)
//        val detailContainer = null
//        detailContainer?.let {
//            displayOnTablet(trailId)
//        } ?: run {
//            Intent(this, TrailDetailsFragment::class.java).apply {
//                putExtra(TrailDetailsFragment.ARG_TRAIL_ID, trailId)
//                startActivity(this)
//            }
//        }
        Intent(this, DetailActivity::class.java).apply {
            putExtra(TrailDetailsFragment.ARG_TRAIL_ID, trailId)
//            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? TrailDetailsFragment)?.apply {
//                arguments = Bundle().apply {
//                    putInt(TrailDetailsFragment.ARG_TRAIL_ID, trailId)
//                }
//            }
            startActivity(this)
        }
    }

    override fun displayOnTablet(trailId: Int) {
//        val details = TrailDetailsFragment().apply {
//            arguments = Bundle().apply {
//                putInt(TrailDetailsFragment.ARG_TRAIL_ID, trailId)
//            }
//        }
//        supportFragmentManager.commit {
//            replace(R.id.detail_fragment_container, details)
//            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            addToBackStack(null)
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try{
            setContentView(R.layout.activity_main)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error setting content view", e)
        }
//        setContentView(R.layout.activity_main)
//        val fragmentContainer = findViewById<View>(R.id.TrailsList)
    }

}
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val db = DatabaseHandler(this)
//        db.insertExampleData()
//        val trails = db.readData()
//        setContent {
//            HikingTrailsTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    TrailsToCards(trails) {  }
////                    ListDetailPaneScaffoldParts(trails)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TrailsToCards(
//    trails: List<Trail>,
//    onItemClick: (TrailIdx) -> Unit,
//) {
//    Card {
//        LazyColumn {
//            trails.forEachIndexed { idx, trail ->
//                item {
//                    ListItem(
//                        modifier = Modifier
//                            .background(Color.Magenta)
//                            .clickable {
//                                onItemClick(TrailIdx(idx))
//                            },
//                        headlineContent = {
//                            Text(trail.name)
//                        },
//                    )
//                }
//            }
//        }
//    }
//}
//
//class TrailIdx(val idx: Int) {
//
//    companion object {
//        val Saver: Saver<TrailIdx?, Int> = Saver(
//            { it?.idx },
//            ::TrailIdx,
//        )
//    }
//}

//
//@OptIn(ExperimentalMaterial3AdaptiveApi::class)
//@Composable
//fun ListDetailPaneScaffoldParts(trails: List<Trail>) {
//    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()
//
//    BackHandler(navigator.canNavigateBack()) {
//        navigator.navigateBack()
//    }
//
//    var selectedItem: TrailIdx? by rememberSaveable(stateSaver = TrailIdx.Saver) {
//        mutableStateOf(null)
//    }
//
//    ListDetailPaneScaffold(
//        directive = navigator.scaffoldDirective,
//        value = navigator.scaffoldValue,
//        listPane = {
//            TrailsToCards(
//                trails,
//                onItemClick = { idx ->
//                    selectedItem = idx
//                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
//                }
//            )
//        },
//        detailPane = {
//            selectedItem?.let { item ->
//                TrailDetails(trails[item.idx])
//            }
//        },
//    )
//}
//

//
//@Composable
//fun TrailDetails(trail : Trail) {
//    val text = trail.name
//    Card {
//        Column(
//            Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Details page for $text",
//                fontSize = 24.sp,
//            )
//            Spacer(Modifier.size(16.dp))
//            Text(
//                text = trail.description
//            )
//        }
//    }
//}
