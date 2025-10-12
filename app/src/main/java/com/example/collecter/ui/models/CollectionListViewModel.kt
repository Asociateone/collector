package com.example.collecter.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collecter.dataObjects.Collection
import com.example.collecter.enums.UiState
import com.example.collecter.repositories.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionListViewModel (val collectionRepository: CollectionRepository) : ViewModel ()
{
    private val _uiState = MutableStateFlow<UiState<List<Collection>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Collection>>> = _uiState

    init {
        // Observe database changes
        viewModelScope.launch(Dispatchers.IO) {
            collectionRepository.getCollectionListFlow().collect { collections ->
                _uiState.value = UiState.Success(collections)
            }
        }
        // Sync with server
        syncCollections()
    }

    fun syncCollections(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            collectionRepository.syncCollections()
        }
    }

    fun refreshCollections(): Unit {
        syncCollections()
    }
}