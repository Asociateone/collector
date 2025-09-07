package com.example.collecter.ui.composables.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.collecter.ui.composables.views.auth.SignInView
import com.example.collecter.ui.models.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(modifier: Modifier = Modifier) {

    val authViewModel: AuthViewModel = koinViewModel()
    val uiState = authViewModel.uiState.collectAsState().value
    SignInView(
        modifier = modifier,
        email = uiState.email,
        password = uiState.password,
        onEmailChange = { authViewModel.setEmail(it) },
        onPasswordChange = { authViewModel.setPassword(it) },
        signIn = { authViewModel.login() },
    )
}