package com.example.collecter.ui.composables.views.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.screens.main.CollectionScreen
import com.example.collecter.ui.models.CollectionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CollectionView(modifier: Modifier = Modifier, collectionId: String, collectionTitle: (String) -> Unit) {
    val collectionViewModel : CollectionViewModel = koinViewModel()
    collectionViewModel.getCollection(collectionId.toInt())

    val collection = collectionViewModel.uiState.collectAsState().value

    if (collection is UiState.Success) {
        collectionTitle(collection.data.title)
    }

    CollectionScreen(modifier, isLoading = collection is UiState.Loading)
}