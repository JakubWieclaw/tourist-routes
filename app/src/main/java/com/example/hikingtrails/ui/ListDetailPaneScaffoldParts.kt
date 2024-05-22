package com.example.hikingtrails.ui

import Trail
import TrailDetails
import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.hikingtrails.TrailIdx

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
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
                // Ensure the index is within the bounds of the list
                val trail = trails.getOrNull(selectedItem.idx)
                trail?.let {
                    TrailDetails(it, context)
                }
            }
        },
    )
}