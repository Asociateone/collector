package com.example.collecter.ui.composables.views.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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

    val games = gameBrowseViewModel.games.collectAsState().value
    val isLoading = gameBrowseViewModel.isLoading.collectAsState().value
    val isLoadingMore = gameBrowseViewModel.isLoadingMore.collectAsState().value
    val searchQuery = gameBrowseViewModel.searchQuery.collectAsState().value
    val genres = gameBrowseViewModel.genres.collectAsState().value
    val platforms = gameBrowseViewModel.platforms.collectAsState().value
    val selectedGenre = gameBrowseViewModel.selectedGenre.collectAsState().value
    val selectedPlatform = gameBrowseViewModel.selectedPlatform.collectAsState().value

    GameBrowseScreen(
        modifier = modifier,
        games = games,
        isLoading = isLoading,
        isLoadingMore = isLoadingMore,
        searchQuery = searchQuery ?: "",
        onSearchQueryChange = { query ->
            gameBrowseViewModel.setSearchQuery(query.ifBlank { null })
        },
        onGameClick = onGameClick,
        onLoadMore = gameBrowseViewModel::loadNextPage,
        genres = genres,
        platforms = platforms,
        selectedGenre = selectedGenre,
        selectedPlatform = selectedPlatform,
        onGenreSelected = gameBrowseViewModel::setGenreFilter,
        onPlatformSelected = gameBrowseViewModel::setPlatformFilter,
        onClearFilters = gameBrowseViewModel::clearFilters
    )
}
