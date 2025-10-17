package com.example.collecter.ui.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.Game
import com.example.collecter.enums.UiState
import com.example.collecter.repositories.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionViewModel (val collectionRepository: CollectionRepository) : ViewModel ()
{
    private val _uiState = MutableStateFlow<UiState<Collection>>(UiState.Loading)
    val uiState: StateFlow<UiState<Collection>> = _uiState

    private val _gamesUiState = MutableStateFlow<UiState<List<Game>>>(UiState.Loading)
    val gamesUiState: StateFlow<UiState<List<Game>>> = _gamesUiState

    fun getCollection(id: Int): Unit {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = collectionRepository.getCollection(id)
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
        viewModelScope.launch(Dispatchers.IO) {
            val result = collectionRepository.updateGameStatus(collectionId, gameId, status)
            if (result is UiState.Success) {
                // Refresh games list
                getCollectionGames(collectionId)
                onSuccess()
            }
        }
    }

    fun removeGameFromCollection(collectionId: Int, gameId: Int, onSuccess: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = collectionRepository.removeGameFromCollection(collectionId, gameId)
            if (result is UiState.Success) {
                // Refresh games list
                getCollectionGames(collectionId)
                onSuccess()
            }
        }
    }
}