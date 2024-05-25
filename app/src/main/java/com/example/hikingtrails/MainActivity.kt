package com.example.hikingtrails

import Trail
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.hikingtrails.ui.ListDetailPaneScaffoldParts
import com.example.hikingtrails.ui.theme.HikingTrailsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DatabaseHandler(this)
        // delete data from the database and then recreate it
        db.deleteData()
        //db.insertExampleData(context = this)
        db.insertAccurateData(context = this)
        val trails = db.readData()
        setContent {
            HikingTrailsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListDetailPaneScaffoldParts(trails, this)
                }
            }
        }
    }
}

//class TrailIdx(val idx: Int) {
//    companion object {
//        val Saver: Saver<TrailIdx?, Int> = Saver(
//            { it?.idx },
//            ::TrailIdx,
//        )
//    }
//}

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
//                            Text(
//                                text = trail.name,
//                            )
//                        },
//                    )
//                }
//            }
//        }
//    }
//}

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
