package com.example.collecter.ui.composables.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collecter.R
import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.Game
import com.example.collecter.ui.composables.views.auth.LoadingView

@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    collection: Collection?,
    games: List<Game>?,
    isLoading: Boolean,
    isGamesLoading: Boolean,
    onGameClick: (Int) -> Unit,
    onAddGame: () -> Unit,
    onToggleStatus: (Int, String) -> Unit,
    onRemoveGame: (Int) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (isLoading) {
            LoadingView(Modifier.fillMaxSize())
        } else if (collection != null) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Collection Header
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        collection.icon?.let { icon ->
                            AsyncImage(
                                model = ImageRequest.Builder(context = LocalContext.current)
                                    .data(icon)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = collection.title,
                                placeholder = painterResource(R.drawable.image),
                                error = painterResource(R.drawable.menu),
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        Text(
                            text = collection.title,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                // Games List
                if (isGamesLoading) {
                    LoadingView(Modifier.fillMaxSize())
                } else if (games != null && games.isNotEmpty()) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(games) { game ->
                            CollectionGameItem(
                                game = game,
                                onGameClick = { onGameClick(game.id) },
                                onToggleStatus = { newStatus ->
                                    onToggleStatus(game.id, newStatus)
                                },
                                onRemoveGame = { onRemoveGame(game.id) }
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No games in this collection yet",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            // FAB to add games
            FloatingActionButton(
                onClick = onAddGame,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add game"
                )
            }
        }
    }
}

@Composable
fun CollectionGameItem(
    game: Game,
    onGameClick: () -> Unit,
    onToggleStatus: (String) -> Unit,
    onRemoveGame: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onGameClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cover Image
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(game.coverUrls?.thumb ?: game.coverUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = game.title,
                placeholder = painterResource(R.drawable.image),
                error = painterResource(R.drawable.image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Game Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                game.rating?.let { rating ->
                    Text(
                        text = "Rating: ${String.format("%.1f", rating)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Status Chips
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = game.status == "wanted",
                        onClick = { onToggleStatus("wanted") },
                        label = { Text("Want") }
                    )
                    FilterChip(
                        selected = game.status == "have",
                        onClick = { onToggleStatus("have") },
                        label = { Text("Have") }
                    )
                }
            }

            // Remove button
            IconButton(onClick = onRemoveGame) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove game",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}