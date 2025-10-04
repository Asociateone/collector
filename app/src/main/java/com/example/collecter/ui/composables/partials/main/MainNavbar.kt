package com.example.collecter.ui.composables.partials.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainNavbar (
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    onNavigateToMore: () -> Unit = {},
    title: String = ""
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = title == "Dashboard",
            onClick = onNavigateToHome,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = title == "More",
            onClick = onNavigateToMore,
            icon = {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More"
                )
            },
            label = { Text("More") }
        )
    }
}