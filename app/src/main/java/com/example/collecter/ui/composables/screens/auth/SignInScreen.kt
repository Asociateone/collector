package com.example.collecter.ui.composables.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.views.auth.SignInView
import com.example.collecter.ui.models.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(modifier: Modifier = Modifier) {

    val authViewModel: AuthViewModel = koinViewModel()
    val viewState = authViewModel.uiState.collectAsState().value
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    if (viewState is UiState.Error) {
        message = viewState.message
    }

    SignInView(
        modifier = modifier,
        email = email,
        password = password,
        errorMessage = message,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        signIn = {
            message = ""
            authViewModel.login(email, password)
                 },
        isLoading = viewState is UiState.Loading
    )
}