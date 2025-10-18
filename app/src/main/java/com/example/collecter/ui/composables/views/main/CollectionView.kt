package com.example.collecter.ui.composables.views.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.screens.main.CollectionScreen
import com.example.collecter.ui.composables.screens.main.EditCollectionDialog
import com.example.collecter.ui.composables.views.auth.LoadingView
import com.example.collecter.ui.models.CollectionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CollectionView(
    modifier: Modifier = Modifier,
    collectionId: String,
    collectionTitle: (String) -> Unit,
    onGameClick: (Int) -> Unit = {},
    onAddGame: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val collectionViewModel : CollectionViewModel = koinViewModel()
    val collectionState = collectionViewModel.uiState.collectAsState().value
    val gamesState = collectionViewModel.gamesUiState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    // Edit dialog state
    var showEditDialog by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf("") }
    var editErrorMessage by remember { mutableStateOf<String?>(null) }
    var isEditLoading by remember { mutableStateOf(false) }

    // Delete confirmation dialog state
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(collectionId) {
        collectionId.toIntOrNull()?.let {
            collectionViewModel.getCollection(it)
            collectionViewModel.getCollectionGames(it)
        }
    }

    when (val collection = collectionState) {
        is UiState.Loading -> {
            Column (modifier.fillMaxSize()) {
                LoadingView()
            }
        }
        is UiState.Success -> {
            collectionTitle(collection.data.title)

            val games = when (gamesState) {
                is UiState.Success -> gamesState.data
                else -> null
            }

            CollectionScreen(
                modifier = modifier,
                collection = collection.data,
                games = games,
                isLoading = false,
                isGamesLoading = gamesState is UiState.Loading,
                onGameClick = onGameClick,
                onAddGame = { status -> onAddGame(status) },
                onToggleStatus = { gameId, newStatus ->
                    collectionId.toIntOrNull()?.let { collId ->
                        collectionViewModel.updateGameStatus(collId, gameId, newStatus)
                    }
                },
                onRemoveGame = { gameId ->
                    collectionId.toIntOrNull()?.let { collId ->
                        collectionViewModel.removeGameFromCollection(collId, gameId)
                    }
                },
                onEdit = {
                    editedTitle = collection.data.title
                    showEditDialog = true
                },
                onDelete = {
                    showDeleteDialog = true
                },
                onNavigateBack = onNavigateBack
            )

            // Edit Collection Dialog
            if (showEditDialog) {
                EditCollectionDialog(
                    collection = collection.data,
                    title = editedTitle,
                    updateTitle = { editedTitle = it },
                    onDismiss = {
                        showEditDialog = false
                        editErrorMessage = null
                    },
                    onSubmit = {
                        collectionId.toIntOrNull()?.let { collId ->
                            isEditLoading = true
                            editErrorMessage = null

                            coroutineScope.launch {
                                collectionViewModel.updateCollection(collId, editedTitle, null)

                                // Wait a bit for the state to update
                                kotlinx.coroutines.delay(500)

                                // Check if update was successful
                                when (collectionViewModel.uiState.value) {
                                    is UiState.Success -> {
                                        collectionTitle(editedTitle)
                                        showEditDialog = false
                                        isEditLoading = false
                                    }
                                    is UiState.Error -> {
                                        val error = collectionViewModel.uiState.value as UiState.Error
                                        editErrorMessage = error.message
                                        isEditLoading = false
                                    }
                                    else -> {
                                        isEditLoading = false
                                    }
                                }
                            }
                        }
                    },
                    isLoading = isEditLoading,
                    errorMessage = editErrorMessage
                )
            }

            // Delete Confirmation Dialog
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { androidx.compose.material3.Text("Delete Collection") },
                    text = { androidx.compose.material3.Text("Are you sure you want to delete \"${collection.data.title}\"? This action cannot be undone.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDeleteDialog = false
                                collectionId.toIntOrNull()?.let { collId ->
                                    collectionViewModel.deleteCollection(collId) {
                                        onNavigateBack()
                                    }
                                }
                            }
                        ) {
                            androidx.compose.material3.Text("Delete", color = androidx.compose.material3.MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            androidx.compose.material3.Text("Cancel")
                        }
                    }
                )
            }
        }
        is UiState.Error -> {
            // TODO: Handle error state
        }
    }
}
