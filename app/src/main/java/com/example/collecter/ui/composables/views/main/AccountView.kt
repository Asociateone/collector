package com.example.collecter.ui.composables.views.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.collecter.ui.composables.screens.main.AccountScreen
import com.example.collecter.ui.models.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountView(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val authViewModel: AuthViewModel = koinViewModel()

    AccountScreen(
        modifier = modifier,
        authViewModel = authViewModel,
        onNavigateBack = onNavigateBack,
        onLogout = onLogout
    )
}
