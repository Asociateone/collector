package com.example.collecter.ui.navigations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collecter.enums.MainNavigation
import com.example.collecter.ui.composables.views.main.DashboardView
import com.example.collecter.ui.theme.CollecterTheme

@Composable
fun MainNavigation(modifier: Modifier = Modifier, navController: NavHostController): Unit {
    CollecterTheme(dynamicColor = false) {
        ModalNavigationDrawer({
            ModalDrawerSheet {
                Text("??", color = Color.Black)
            }
        }) {
            NavHost(
                modifier = modifier,
                navController = navController,
                startDestination = MainNavigation.Dashboard.name,
                enterTransition = { fadeIn(tween(0)) },
                exitTransition = { fadeOut(tween(0)) },
            ) {
                composable(MainNavigation.Dashboard.name) {
                    DashboardView(Modifier.fillMaxSize())
                }
            }
}}}