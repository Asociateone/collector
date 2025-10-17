package com.example.collecter.ui.composables.views.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.screens.main.GameBrowseScreen
import com.example.collecter.ui.models.GameBrowseViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GameBrowseView(
    modifier: Modifier = Modifier,
    collectionId: String?,
    onGameClick: (Int) -> Unit = {}
) {
    val gameBrowseViewModel: GameBrowseViewModel = koinViewModel()
    val uiState = gameBrowseViewModel.uiState.collectAsState().value
    val searchQuery = gameBrowseViewModel.searchQuery.collectAsState().value
    val currentPage = gameBrowseViewModel.currentPage.collectAsState().value

    val games = when (uiState) {
        is UiState.Success -> uiState.data
        else -> null
    }

    GameBrowseScreen(
        modifier = modifier,
        games = games,
        isLoading = uiState is UiState.Loading,
        searchQuery = searchQuery ?: "",
        onSearchQueryChange = { query ->
            gameBrowseViewModel.setSearchQuery(query.ifBlank { null })
        },
        onGameClick = onGameClick,
        onNextPage = gameBrowseViewModel::nextPage,
        onPreviousPage = gameBrowseViewModel::previousPage,
        currentPage = currentPage,
        lastPage = games?.meta?.lastPage ?: 1
    )
}
