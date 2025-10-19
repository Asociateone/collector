package com.example.collecter.ui.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.Game
import com.example.collecter.enums.UiState
import com.example.collecter.enums.WebState
import com.example.collecter.repositories.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionViewModel (val collectionRepository: CollectionRepository) : ViewModel ()
{
    private val _uiState = MutableStateFlow<UiState<Collection>>(UiState.Loading)
    val uiState: StateFlow<UiState<Collection>> = _uiState

    private val _gamesUiState = MutableStateFlow<UiState<List<Game>>>(UiState.Loading)
    val gamesUiState: StateFlow<UiState<List<Game>>> = _gamesUiState

    fun getCollection(id: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            collectionRepository.getCollectionFlow(id).collect { collection ->
                _uiState.value = UiState.Success(collection)
            }
        }
    }

    fun createCollection(title: String, icon: String? = null) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = collectionRepository.createCollection(title, icon)
        }
    }

    fun updateCollection(collectionId: Int, title: String?, icon: String?) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = collectionRepository.updateCollection(collectionId, title, icon)
        }
    }

    fun deleteCollection(collectionId: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = collectionRepository.deleteCollection(collectionId)
            if (result is UiState.Success) {
                onSuccess()
            }
        }
    }

    // Game management methods
    fun getCollectionGames(collectionId: Int, status: String? = null) {
        _gamesUiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _gamesUiState.value = collectionRepository.getCollectionGames(collectionId, status)
        }
    }

    fun addGameToCollection(collectionId: Int, gameId: Int, status: String = "wanted", onSuccess: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = collectionRepository.addGameToCollection(collectionId, gameId, status)
            if (result is UiState.Success) {
                // Refresh games list
                getCollectionGames(collectionId)
                onSuccess()
            }
        }
    }

    fun updateGameStatus(collectionId: Int, gameId: Int, status: String, onSuccess: () -> Unit = {}) {
        // Optimistically update UI by changing the game status in the list
        val currentState = _gamesUiState.value
        if (currentState is UiState.Success) {
            val updatedGames = currentState.data.map { game ->
                if (game.id == gameId) {
                    game.copy(status = status)
                } else {
                    game
                }
            }
            _gamesUiState.value = UiState.Success(updatedGames)
        }

        // Make API call in the background
        viewModelScope.launch(Dispatchers.IO) {
            val result = collectionRepository.updateGameStatus(collectionId, gameId, status)
            if (result is UiState.Success) {
                onSuccess()
                // Silently refresh to ensure data consistency
                refreshCollectionGamesInBackground(collectionId)
            } else {
                // If update failed, refresh the list to revert the optimistic update
                getCollectionGames(collectionId)
            }
        }
    }

    fun removeGameFromCollection(collectionId: Int, gameId: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            // Wait for swipe animation to complete (Material3 animation is ~300ms)
            delay(350)

            // Optimistically update UI by removing the game from the list
            val currentState = _gamesUiState.value
            if (currentState is UiState.Success) {
                val updatedGames = currentState.data.filter { it.id != gameId }
                _gamesUiState.value = UiState.Success(updatedGames)
            }
        }

        // Make API call in the background
        viewModelScope.launch(Dispatchers.IO) {
            val result = collectionRepository.removeGameFromCollection(collectionId, gameId)
            if (result is UiState.Success) {
                onSuccess()
                // Silently refresh to ensure data consistency
                refreshCollectionGamesInBackground(collectionId)
            } else {
                // If removal failed, refresh the list to revert the optimistic update
                getCollectionGames(collectionId)
            }
        }
    }

    private fun refreshCollectionGamesInBackground(collectionId: Int, status: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = collectionRepository.getCollectionGames(collectionId, status)
            // Only update if we got successful data, don't change to Loading state
            if (result is UiState.Success) {
                _gamesUiState.value = result
            }
        }
    }
}