package com.example.collecter.ui.models

import android.util.Log
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
        getCollectionList()
    }

    fun getCollectionList(): Unit {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = collectionRepository.getCollections()
        }
    }
}