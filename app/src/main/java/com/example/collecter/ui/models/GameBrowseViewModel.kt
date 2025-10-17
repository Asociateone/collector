package com.example.collecter.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collecter.dataObjects.Game
import com.example.collecter.dataObjects.PaginatedResponse
import com.example.collecter.enums.UiState
import com.example.collecter.repositories.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameBrowseViewModel(val gameRepository: GameRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<PaginatedResponse<Game>>>(UiState.Loading)
    val uiState: StateFlow<UiState<PaginatedResponse<Game>>> = _uiState

    private val _searchQuery = MutableStateFlow<String?>(null)
    private val _genreFilter = MutableStateFlow<Int?>(null)
    private val _platformFilter = MutableStateFlow<Int?>(null)
    private val _currentPage = MutableStateFlow(1)

    init {
        browseGames()
    }

    fun browseGames(
        search: String? = _searchQuery.value,
        genre: Int? = _genreFilter.value,
        platform: Int? = _platformFilter.value,
        page: Int = _currentPage.value
    ) {
        _uiState.value = UiState.Loading
        _searchQuery.value = search
        _genreFilter.value = genre
        _platformFilter.value = platform
        _currentPage.value = page

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = gameRepository.browseGames(search, genre, platform, page)
        }
    }

    fun setSearchQuery(query: String?) {
        browseGames(search = query, page = 1)
    }

    fun setGenreFilter(genre: Int?) {
        browseGames(genre = genre, page = 1)
    }

    fun setPlatformFilter(platform: Int?) {
        browseGames(platform = platform, page = 1)
    }

    fun loadPage(page: Int) {
        browseGames(page = page)
    }

    fun nextPage() {
        loadPage(_currentPage.value + 1)
    }

    fun previousPage() {
        if (_currentPage.value > 1) {
            loadPage(_currentPage.value - 1)
        }
    }
}
