package com.example.collecter.ui.composables.partials.auth

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.collecter.ui.navigations.AuthenticationNavigation

@Composable
fun AuthenticationScaffold(navHostController: NavHostController): Unit
{
    Scaffold (
        topBar = { AuthenticationNavBar(modifier = Modifier, navController = navHostController) }
    ) { innerPadding ->
        AuthenticationNavigation(Modifier.padding(innerPadding), navHostController)
    }
}