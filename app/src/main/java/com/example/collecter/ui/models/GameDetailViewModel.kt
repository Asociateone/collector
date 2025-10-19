package com.example.collecter.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collecter.dataObjects.Game
import com.example.collecter.enums.UiState
import com.example.collecter.repositories.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameDetailViewModel(val gameRepository: GameRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Game>>(UiState.Loading)
    val uiState: StateFlow<UiState<Game>> = _uiState

    fun getGame(gameId: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = gameRepository.getGame(gameId)
        }
    }
}
