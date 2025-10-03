package com.example.collecter.ui.composables.views.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.screens.main.CollectionScreen
import com.example.collecter.ui.composables.views.auth.LoadingView
import com.example.collecter.ui.models.CollectionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CollectionView(modifier: Modifier = Modifier, collectionId: String, collectionTitle: (String) -> Unit) {
    val collectionViewModel : CollectionViewModel = koinViewModel()

    LaunchedEffect(collectionId) {
        collectionViewModel.getCollection(collectionId.toInt())
    }

    when (val collection = collectionViewModel.uiState.collectAsState().value) {
        is UiState.Loading -> {
            Column (modifier.fillMaxSize()) {
                LoadingView()
            }
        }
        is UiState.Success -> {
            collectionTitle(collection.data.title)
            CollectionScreen(
                modifier,
            )
        }
        is UiState.Error -> {
            // TODO: Handle error state
        }
    }
}