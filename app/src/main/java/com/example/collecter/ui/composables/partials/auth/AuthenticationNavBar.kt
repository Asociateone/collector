package com.example.collecter.ui.composables.partials.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.collecter.enums.AuthNavigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationNavBar(modifier: Modifier = Modifier, navController: NavHostController): Unit {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    if (navBackStackEntry?.destination?.route.toString() != AuthNavigation.SignIn.name) {
        CenterAlignedTopAppBar(
            title = { },
            modifier = modifier.fillMaxWidth(),
            navigationIcon = {
                IconButton(
                    onClick = { navController.navigateUp() },
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }
}
