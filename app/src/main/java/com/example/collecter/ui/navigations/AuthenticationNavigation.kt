package com.example.collecter.ui.navigations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collecter.enums.AuthNavigation
import com.example.collecter.ui.composables.screens.auth.SignInScreen
import com.example.collecter.ui.composables.screens.auth.SignUpScreen

@Composable
public fun AuthenticationNavigation(modifier: Modifier = Modifier, navController: NavHostController): Unit
{
    NavHost(
        navController= navController,
        startDestination = AuthNavigation.SignIn.name,
        enterTransition = { fadeIn(tween(0)) },
        exitTransition = { fadeOut(tween(0)) },
    ) {
        composable(AuthNavigation.SignIn.name) {
            SignInScreen(modifier.fillMaxSize(), {
                navController.navigate(AuthNavigation.SignUp.name)
            })
        }
        composable(AuthNavigation.SignUp.name) {
            SignUpScreen(modifier.fillMaxSize())
        }
    }
}