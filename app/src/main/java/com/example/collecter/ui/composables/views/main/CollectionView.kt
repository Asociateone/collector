package com.example.collecter.ui.composables.views.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.screens.main.CollectionScreen
import com.example.collecter.ui.composables.views.auth.LoadingView
import com.example.collecter.ui.models.CollectionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CollectionView(
    modifier: Modifier = Modifier,
    collectionId: String,
    collectionTitle: (String) -> Unit,
    onBackClick: () -> Unit = {}
) {
    val collectionViewModel : CollectionViewModel = koinViewModel()
    val showMore = remember { mutableStateOf(false) }
    val isDeleting = remember { mutableStateOf(false) }

    LaunchedEffect(collectionId) {
        collectionId.toIntOrNull()?.let {
            collectionViewModel.getCollection(it)
        }
    }

    LaunchedEffect(isDeleting.value, collectionViewModel.uiState.collectAsState().value) {
        if (isDeleting.value && collectionViewModel.uiState.value !is UiState.Success) {
            onBackClick()
        }
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
                modifier = modifier,
                title = collection.data.title,
                onBackClick = onBackClick,
                toggleShowMore = {showMore.value = !showMore.value},
                showMore = showMore.value,
                onDeleteClick = {
                    isDeleting.value = true
                    collectionViewModel.deleteCollection(collection.data.id)
                }
            )
        }
        is UiState.Error -> {
        }
    }
}