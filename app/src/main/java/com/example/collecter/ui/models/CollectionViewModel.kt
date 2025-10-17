package com.example.collecter.ui.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collecter.dataObjects.Collection
import com.example.collecter.enums.UiState
import com.example.collecter.enums.WebState
import com.example.collecter.repositories.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionViewModel (val collectionRepository: CollectionRepository) : ViewModel ()
{
    private val _uiState = MutableStateFlow<UiState<Collection>>(UiState.Loading)

    val uiState: StateFlow<UiState<Collection>> = _uiState

    fun getCollection(id: Int)  {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            collectionRepository.getCollectionFlow(id).collect { collection ->
                _uiState.value = UiState.Success(collection)
            }
        }
    }

    fun createCollection(title: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = collectionRepository.createCollection(title)) {
                is WebState.Success -> {
                    _uiState.value = UiState.Success(result.data)
                }
                is WebState.Error -> {
                    _uiState.value = UiState.Error(result.message, result.errors)
                }
                else -> {
                    _uiState.value = UiState.Error("Unknown error")
                }
            }
        }
    }

    fun deleteCollection(collectionId: Int)
    {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = collectionRepository.deleteCollection(collectionId)
            when (result) {
                is WebState.Success -> {
                    _uiState.value = UiState.Error("Deleted")
                }
                is WebState.Error -> {
                    _uiState.value = UiState.Error(result.message, result.errors)
                }
                else -> {
                    _uiState.value = UiState.Error("Unknown error")
                }
            }
        }
    }
}