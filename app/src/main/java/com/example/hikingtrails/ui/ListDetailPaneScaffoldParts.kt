package com.example.hikingtrails.ui

import Trail
import TrailDetails
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.hikingtrails.TrailIdx

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalFoundationApi::class)
@Composable
fun ListDetailPaneScaffoldParts(trails: List<Trail>, context: android.content.Context) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    var selectedItem by rememberSaveable(stateSaver = TrailIdx.Saver) {
        mutableStateOf(TrailIdx.None)
    }


    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            TrailsToCards(
                trails,
                onItemClick = { idx ->
                    selectedItem = idx
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                }
            )
        },
        detailPane = {
            if (selectedItem != TrailIdx.None) {
                val pagerState =
                    rememberPagerState(pageCount = { trails.size }, initialPage = selectedItem.idx)
                LaunchedEffect(selectedItem) {
                    pagerState.scrollToPage(selectedItem.idx)
                }
                HorizontalPager(state = pagerState) { page ->
                    val trail = trails.getOrNull(page)
                    trail?.let {
                        TrailDetails(it, context)
                    }

                }
            }
        },
    )
}

