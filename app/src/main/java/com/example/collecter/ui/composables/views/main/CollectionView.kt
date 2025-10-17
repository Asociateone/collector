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
fun CollectionView(
    modifier: Modifier = Modifier,
    collectionId: String,
    collectionTitle: (String) -> Unit,
    onGameClick: (Int) -> Unit = {},
    onAddGame: (String) -> Unit = {}
) {
    val collectionViewModel : CollectionViewModel = koinViewModel()
    val collectionState = collectionViewModel.uiState.collectAsState().value
    val gamesState = collectionViewModel.gamesUiState.collectAsState().value

    LaunchedEffect(collectionId) {
        collectionId.toIntOrNull()?.let {
            collectionViewModel.getCollection(it)
            collectionViewModel.getCollectionGames(it)
        }
    }

    when (val collection = collectionState) {
        is UiState.Loading -> {
            Column (modifier.fillMaxSize()) {
                LoadingView()
            }
        }
        is UiState.Success -> {
            collectionTitle(collection.data.title)

            val games = when (gamesState) {
                is UiState.Success -> gamesState.data
                else -> null
            }

            CollectionScreen(
                modifier = modifier,
                collection = collection.data,
                games = games,
                isLoading = false,
                isGamesLoading = gamesState is UiState.Loading,
                onGameClick = onGameClick,
                onAddGame = { status -> onAddGame(status) },
                onToggleStatus = { gameId, newStatus ->
                    collectionId.toIntOrNull()?.let { collId ->
                        collectionViewModel.updateGameStatus(collId, gameId, newStatus)
                    }
                },
                onRemoveGame = { gameId ->
                    collectionId.toIntOrNull()?.let { collId ->
                        collectionViewModel.removeGameFromCollection(collId, gameId)
                    }
                }
            )
        }
        is UiState.Error -> {
            // TODO: Handle error state
        }
    }
}
