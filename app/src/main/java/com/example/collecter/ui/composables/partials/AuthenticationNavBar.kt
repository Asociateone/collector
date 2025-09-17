package com.example.collecter.ui.composables.partials

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController


@Composable
fun AuthenticationNavBar(modifier: Modifier = Modifier, navController: NavHostController) {
    NavigationBar(
        modifier,
        Color.Transparent,
        Color.Black
    ) {
        Column {
            IconButton({ navController.navigateUp() }, Modifier, true) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
        }
    }
}