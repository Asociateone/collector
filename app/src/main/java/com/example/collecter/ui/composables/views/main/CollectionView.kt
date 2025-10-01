package com.example.collecter.ui.composables.views.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.screens.main.DashboardScreen
import com.example.collecter.ui.models.CollectionListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CollectionView(collectionId: String) {
    Column () {
        Text("Collection Show ${collectionId}")
    }
}