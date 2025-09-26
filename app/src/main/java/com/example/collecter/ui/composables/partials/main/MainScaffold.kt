package com.example.collecter.ui.composables.partials.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.collecter.ui.navigations.MainNavigation

@Composable
fun MainScaffold(navController: NavHostController): Unit
{
    Scaffold(modifier = Modifier) { paddingValues ->
        MainNavigation(Modifier.padding(paddingValues), navController)
    }
}