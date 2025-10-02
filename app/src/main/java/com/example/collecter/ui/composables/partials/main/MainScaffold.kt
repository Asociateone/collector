package com.example.collecter.ui.composables.partials.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.collecter.ui.navigations.MainNavigation

@Composable
fun MainScaffold(navController: NavHostController): Unit
{
    MainNavigation(Modifier, navController)
}