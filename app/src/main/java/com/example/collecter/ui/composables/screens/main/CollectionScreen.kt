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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.collecter.R
import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.Game
import com.example.collecter.ui.composables.partials.Button
import com.example.collecter.ui.composables.partials.formFields.TextInputField
import com.example.collecter.ui.composables.views.auth.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    collection: Collection?,
    games: List<Game>?,
    isLoading: Boolean,
    isGamesLoading: Boolean,
    onGameClick: (Int) -> Unit,
    onAddGame: (String) -> Unit,
    onToggleStatus: (Int, String) -> Unit,
    onRemoveGame: (Int) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Want", "Have")
    val currentStatus = if (selectedTabIndex == 0) "wanted" else "have"
    var showDropdownMenu by remember { mutableStateOf(false) }

    // Filter games based on selected tab
    val filteredGames = games?.filter { game ->
        game.status == currentStatus
    }

    if (isLoading) {
        LoadingView(Modifier.fillMaxSize())
    } else if (collection != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = collection.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Navigate back"
                            )
                        }
                    },
                    actions = {
                        Box {
                            IconButton(onClick = { showDropdownMenu = true }) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = "More options"
                                )
                            }
                            DropdownMenu(
                                expanded = showDropdownMenu,
                                onDismissRequest = { showDropdownMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Edit") },
                                    onClick = {
                                        showDropdownMenu = false
                                        onEdit()
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Edit,
                                            contentDescription = "Edit"
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Delete") },
                                    onClick = {
                                        showDropdownMenu = false
                                        onDelete()
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onAddGame(currentStatus) },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 12.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add game",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                // Tabs for Want/Have
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = if (selectedTabIndex == index)
                                        androidx.compose.ui.text.font.FontWeight.Bold
                                    else
                                        androidx.compose.ui.text.font.FontWeight.Normal
                                )
                            }
                        )
                    }
                }

                // Games List
                if (isGamesLoading) {
                    LoadingView(Modifier.fillMaxSize())
                } else if (filteredGames != null && filteredGames.isNotEmpty()) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredGames) { game ->
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionGameItem(
    game: Game,
    onGameClick: () -> Unit,
    onToggleStatus: (String) -> Unit,
    onRemoveGame: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onRemoveGame()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        enableDismissFromStartToEnd = false
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onGameClick),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
            // Cover Image with enhanced styling
            Card(
                modifier = Modifier.shadow(4.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box {
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
                            .width(90.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    // Rating badge overlay
                    game.rating?.let { rating ->
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(6.dp)
                                .background(
                                    Color(0xFF39FF14).copy(alpha = 0.95f), // Neon Green
                                    RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = String.format("%.1f", rating),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Black, // Black text on neon green
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Game Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Status Chips with enhanced styling
                Row(
                    modifier = Modifier.padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = game.status == "wanted",
                        onClick = { onToggleStatus("wanted") },
                        label = {
                            Text(
                                "Want",
                                fontWeight = if (game.status == "wanted")
                                    androidx.compose.ui.text.font.FontWeight.Bold
                                else
                                    androidx.compose.ui.text.font.FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF00FFFF).copy(alpha = 0.9f), // Electric Cyan
                            selectedLabelColor = Color.Black
                        )
                    )
                    FilterChip(
                        selected = game.status == "have",
                        onClick = { onToggleStatus("have") },
                        label = {
                            Text(
                                "Have",
                                fontWeight = if (game.status == "have")
                                    androidx.compose.ui.text.font.FontWeight.Bold
                                else
                                    androidx.compose.ui.text.font.FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFFF1493).copy(alpha = 0.9f), // Hot Pink
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }
    }
    }
}

@Composable
fun EditCollectionDialog(
    collection: Collection,
    title: String = "",
    updateTitle: (String) -> Unit = {},
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clickable(enabled = false) {},
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Edit Collection",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                    TextInputField(
                        title,
                        onValueChange = updateTitle,
                        modifier = Modifier.fillMaxWidth(),
                        placeholderText = "Title",
                    )
                    Button(
                        modifier = Modifier,
                        value = "Save",
                        onClick = onSubmit,
                        isLoading = isLoading
                    )
                }
            }
        }
    }
}