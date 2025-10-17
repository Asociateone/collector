package com.example.collecter.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collecter.dataObjects.Game
import com.example.collecter.dataObjects.Genre
import com.example.collecter.dataObjects.Platform
import com.example.collecter.enums.UiState
import com.example.collecter.repositories.CollectionRepository
import com.example.collecter.repositories.GameRepository
import com.example.collecter.services.HTTP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameBrowseViewModel(
    val gameRepository: GameRepository,
    val collectionRepository: CollectionRepository,
    val http: HTTP
) : ViewModel() {
    // Game list with infinite scroll support
    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore

    // Filter states
    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery

    private val _selectedGenre = MutableStateFlow<Genre?>(null)
    val selectedGenre: StateFlow<Genre?> = _selectedGenre

    private val _selectedPlatform = MutableStateFlow<Platform?>(null)
    val selectedPlatform: StateFlow<Platform?> = _selectedPlatform

    // Available filters
    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> = _genres

    private val _platforms = MutableStateFlow<List<Platform>>(emptyList())
    val platforms: StateFlow<List<Platform>> = _platforms

    // Pagination
    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    private val _hasMorePages = MutableStateFlow(true)
    val hasMorePages: StateFlow<Boolean> = _hasMorePages

    init {
        loadFilters()
        loadGames()
    }

    private fun loadFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            // Load genres
            val genresResult = http.getGenres()
            if (genresResult is UiState.Success) {
                _genres.value = genresResult.data
                android.util.Log.d("GameBrowseViewModel", "Loaded ${genresResult.data.size} genres")
            } else {
                android.util.Log.e("GameBrowseViewModel", "Failed to load genres: $genresResult")
            }

            // Load platforms
            val platformsResult = http.getPlatforms()
            if (platformsResult is UiState.Success) {
                _platforms.value = platformsResult.data
                android.util.Log.d("GameBrowseViewModel", "Loaded ${platformsResult.data.size} platforms")
            } else {
                android.util.Log.e("GameBrowseViewModel", "Failed to load platforms: $platformsResult")
            }
        }
    }

    private fun loadGames(append: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            if (append) {
                _isLoadingMore.value = true
            } else {
                _isLoading.value = true
                _games.value = emptyList()
                _currentPage.value = 1
            }

            val result = gameRepository.browseGames(
                search = _searchQuery.value,
                genre = _selectedGenre.value?.id,
                platform = _selectedPlatform.value?.id,
                page = _currentPage.value
            )

            if (result is UiState.Success) {
                if (append) {
                    _games.value = _games.value + result.data.data
                } else {
                    _games.value = result.data.data
                }
                _hasMorePages.value = _currentPage.value < result.data.meta.lastPage
            }

            _isLoading.value = false
            _isLoadingMore.value = false
        }
    }

    fun setSearchQuery(query: String?) {
        _searchQuery.value = query
        loadGames()
    }

    fun setGenreFilter(genre: Genre?) {
        _selectedGenre.value = genre
        loadGames()
    }

    fun setPlatformFilter(platform: Platform?) {
        _selectedPlatform.value = platform
        loadGames()
    }

    fun clearFilters() {
        _searchQuery.value = null
        _selectedGenre.value = null
        _selectedPlatform.value = null
        loadGames()
    }

    fun loadNextPage() {
        if (_hasMorePages.value && !_isLoadingMore.value) {
            _currentPage.value += 1
            loadGames(append = true)
        }
    }

    fun addGameToCollection(collectionId: Int, gameId: Int, onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = collectionRepository.addGameToCollection(collectionId, gameId, "wanted")
            if (result is UiState.Success) {
                android.util.Log.d("GameBrowseViewModel", "Game added to collection successfully")
                onSuccess()
            } else if (result is UiState.Error) {
                android.util.Log.e("GameBrowseViewModel", "Failed to add game: ${result.message}")
                onError(result.message)
            }
        }
    }
}
