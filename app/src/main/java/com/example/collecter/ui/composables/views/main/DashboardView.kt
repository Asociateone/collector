package com.example.collecter.ui.composables.views.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.screens.main.DashboardScreen
import com.example.collecter.ui.models.CollectionListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardView(modifier: Modifier = Modifier, goToCollection : (Int) -> Unit) {
    val collectionListViewModel: CollectionListViewModel = koinViewModel()
    val viewState = collectionListViewModel.uiState.collectAsState().value

    DashboardScreen(
        modifier = modifier,
        collectionList = if (viewState is UiState.Success) viewState.data else emptyList(),
        isLoading = viewState is UiState.Loading,
        isCreating = false,
        goToCollection = goToCollection,
        createCollection = {}
    )
}