package com.example.collecter.ui.composables.views.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    val viewStateCollectionList = collectionListViewModel.uiState.collectAsState().value
    val viewStateCollection = collectionViewModel.uiState.collectAsState().value
    val showCreateOverlay = remember { mutableStateOf(false) }
    val newCollectionTitle = remember { mutableStateOf("") }
    val searchQuery = remember { mutableStateOf("") }
    val isCreatingInProgress = remember { mutableStateOf(false) }
    val createErrorMessage = remember { mutableStateOf<String?>(null) }
    val filteredCollections = if (viewStateCollectionList is UiState.Success) {
        viewStateCollectionList.data.filter { collection ->
            collection.title.contains(searchQuery.value, ignoreCase = true)
        }
    } else {
        emptyList()
    }

    when (viewStateCollection) {
        is UiState.Success -> {
            if (isCreatingInProgress.value) {
                newCollectionTitle.value = ""
                showCreateOverlay.value = false
                isCreatingInProgress.value = false
                createErrorMessage.value = null
                collectionListViewModel.uiState
            }
        }
        is UiState.Error -> {
            createErrorMessage.value = viewStateCollection.message
            if (isCreatingInProgress.value) {
                isCreatingInProgress.value = false
            }
        }
        else -> {}
    }

    DashboardScreen(
        modifier = modifier,
        collectionList = filteredCollections,
        isLoading = viewStateCollectionList is UiState.Loading,
        searchQuery = searchQuery.value,
        onSearchQueryChange = { searchQuery.value = it },
        isCreating = showCreateOverlay.value,
        isCreatingLoading = isCreatingInProgress.value && viewStateCollection is UiState.Loading,
        createErrorMessage = createErrorMessage.value,
        goToCollection = goToCollection,
        createCollection = { showCreateOverlay.value = true },
        onDismissCreate = {
            showCreateOverlay.value = false
            isCreatingInProgress.value = false
        },
        newCollectionTitle = newCollectionTitle.value,
        updateNewCollectionTitle = {newCollectionTitle.value = it},
        submitNewCollection = {
            isCreatingInProgress.value = true
            collectionViewModel.createCollection(newCollectionTitle.value)
        }
    )
}