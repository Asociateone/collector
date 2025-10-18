package com.example.collecter.ui.composables.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.example.collecter.dataObjects.Game
import com.example.collecter.dataObjects.Genre
import com.example.collecter.dataObjects.Platform
import com.example.collecter.ui.composables.partials.formFields.TextInputField
import com.example.collecter.ui.composables.views.auth.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameBrowseScreen(
    modifier: Modifier = Modifier,
    games: List<Game>,
    isLoading: Boolean,
    isLoadingMore: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onGameClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    genres: List<Genre>,
    platforms: List<Platform>,
    selectedGenre: Genre?,
    selectedPlatform: Platform?,
    onGenreSelected: (Genre?) -> Unit,
    onPlatformSelected: (Platform?) -> Unit,
    onClearFilters: () -> Unit
) {
    var genreExpanded by remember { mutableStateOf(false) }
    var platformExpanded by remember { mutableStateOf(false) }
    val gridState = rememberLazyGridState()

    // Detect when user reaches bottom for infinite scroll
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = gridState.layoutInfo.totalItemsCount
            lastVisibleItem != null && lastVisibleItem.index >= totalItems - 4
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !isLoadingMore && !isLoading && games.isNotEmpty()) {
            onLoadMore()
        }
    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        // Search bar
        TextInputField(
            searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholderText = "Search games..."
        )

        // Filter dropdowns section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Genre dropdown
                ExposedDropdownMenuBox(
                    expanded = genreExpanded,
                    onExpandedChange = { genreExpanded = !genreExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedGenre?.name ?: "All Genres",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = genreExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    ExposedDropdownMenu(
                        expanded = genreExpanded,
                        onDismissRequest = { genreExpanded = false },
                        modifier = Modifier.heightIn(max = 400.dp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("All Genres") },
                            onClick = {
                                onGenreSelected(null)
                                genreExpanded = false
                            }
                        )
                        genres.forEach { genre ->
                            DropdownMenuItem(
                                text = { Text(genre.name) },
                                onClick = {
                                    onGenreSelected(genre)
                                    genreExpanded = false
                                }
                            )
                        }
                    }
                }

                // Platform dropdown
                ExposedDropdownMenuBox(
                    expanded = platformExpanded,
                    onExpandedChange = { platformExpanded = !platformExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedPlatform?.name ?: "All Platforms",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = platformExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    ExposedDropdownMenu(
                        expanded = platformExpanded,
                        onDismissRequest = { platformExpanded = false },
                        modifier = Modifier.heightIn(max = 400.dp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("All Platforms") },
                            onClick = {
                                onPlatformSelected(null)
                                platformExpanded = false
                            }
                        )
                        platforms.forEach { platform ->
                            DropdownMenuItem(
                                text = { Text(platform.name) },
                                onClick = {
                                    onPlatformSelected(platform)
                                    platformExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Clear filters button
            if (selectedGenre != null || selectedPlatform != null || searchQuery.isNotBlank()) {
                FilterChip(
                    selected = false,
                    onClick = onClearFilters,
                    label = { Text("Clear All Filters") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear filters",
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        labelColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Games grid
        Box(modifier = Modifier.weight(1f)) {
            when {
                isLoading && games.isEmpty() -> {
                    LoadingView(Modifier.fillMaxSize())
                }
                games.isEmpty() && !isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No games found",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(games) { game ->
                            GameCard(
                                game = game,
                                onClick = { onGameClick(game.id) }
                            )
                        }

                        // Loading indicator at the bottom
                        if (isLoadingMore) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameCard(
    game: Game,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            Column {
                // Cover image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
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
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    )

                    // Gradient overlay for better text readability
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f)
                                    )
                                )
                            )
                    )

                    // Rating badge
                    game.rating?.let { rating ->
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .background(
                                    Color(0xFF39FF14).copy(alpha = 0.95f), // Neon Green
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = String.format("%.1f", rating),
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Black, // Black text on neon green
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        }
                    }
                }

                // Game info
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(14.dp)
                ) {
                    Text(
                        text = game.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (game.platforms.isNotEmpty()) {
                        Text(
                            text = game.platforms.joinToString(", ") { it.name },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

