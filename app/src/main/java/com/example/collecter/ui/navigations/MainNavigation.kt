package com.example.collecter.ui.navigations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collecter.enums.MainNavigation
import com.example.collecter.ui.composables.partials.main.MainNavbar
import com.example.collecter.ui.composables.views.main.CollectionView
import com.example.collecter.ui.composables.views.main.DashboardView
import com.example.compose.CollecterTheme
import kotlinx.coroutines.launch

@Composable
fun MainNavigation(modifier: Modifier = Modifier, navController: NavHostController): Unit {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val title = remember { mutableStateOf("") }

    CollecterTheme() {
        ModalNavigationDrawer({
            ModalDrawerSheet {
                Text("??")
            }
        }, drawerState = drawerState) {
            Scaffold(
                topBar = {
                    MainNavbar(Modifier, openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }, title.value)
                },
                contentWindowInsets = WindowInsets(10.dp, 10.dp, 10.dp, 10.dp)
            ) { innerPadding ->
                NavHost(
                    modifier = modifier.padding(innerPadding),
                    navController = navController,
                    startDestination = MainNavigation.Dashboard.name,
                    enterTransition = { fadeIn(tween(0)) },
                    exitTransition = { fadeOut(tween(0)) },
                ) {
                    composable(MainNavigation.Dashboard.name) {
                        DashboardView(Modifier.fillMaxSize(), {
                            navController.navigate("${MainNavigation.Dashboard.name}/$it")
                        })
                    }
                    composable("${MainNavigation.Dashboard.name}/{collectionId}") {
                        val collectionId =
                            navController.currentBackStackEntry?.arguments?.getString("collectionId")
                                ?: ""
                        CollectionView(
                            Modifier.fillMaxSize(),
                            collectionId,
                            { title.value = it },
                        )
                    }
                }
            }
        }
    }
}