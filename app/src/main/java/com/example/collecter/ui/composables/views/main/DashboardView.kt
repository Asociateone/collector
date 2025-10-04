package com.example.collecter.ui.composables.views.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.screens.main.DashboardScreen
import com.example.collecter.ui.models.CollectionListViewModel
import com.example.collecter.ui.models.CollectionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardView(modifier: Modifier = Modifier, goToCollection : (Int) -> Unit) {
    val collectionListViewModel: CollectionListViewModel = koinViewModel()
    val collectionViewModel: CollectionViewModel = koinViewModel()

    val viewState = collectionListViewModel.uiState.collectAsState().value
    val viewStateCollection = collectionViewModel.uiState.collectAsState().value
    val showCreateOverlay = remember { mutableStateOf(false) }
    val newCollectionTitle = remember { mutableStateOf("") }
    val searchQuery = remember { mutableStateOf("") }

    val filteredCollections = if (viewState is UiState.Success) {
        viewState.data.filter { collection ->
            collection.title.contains(searchQuery.value, ignoreCase = true)
        }
    } else {
        emptyList()
    }

    DashboardScreen(
        modifier = modifier,
        collectionList = filteredCollections,
        isLoading = viewState is UiState.Loading,
        searchQuery = searchQuery.value,
        onSearchQueryChange = { searchQuery.value = it },
        isCreating = showCreateOverlay.value,
        isCreatingLoading = viewStateCollection is UiState.Loading,
        goToCollection = goToCollection,
        createCollection = { showCreateOverlay.value = true },
        onDismissCreate = { showCreateOverlay.value = false },
        newCollectionTitle = newCollectionTitle.value,
        updateNewCollectionTitle = {newCollectionTitle.value = it},
        submitNewCollection = {
            collectionViewModel.createCollection(newCollectionTitle.value)
            when (viewStateCollection) {
                is UiState.Success -> {
                    newCollectionTitle.value = ""
                    showCreateOverlay.value = false
                    collectionListViewModel.getCollectionList()
                }
                else -> {}
            }
        }
    )
}