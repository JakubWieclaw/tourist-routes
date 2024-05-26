package com.example.hikingtrails.ui

import Trail
import TrailDetails
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

