package com.example.collecter.ui.composables.screens.auth

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.views.auth.LoadingView
import com.example.collecter.ui.composables.views.auth.SignUpView
import com.example.collecter.ui.models.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
public fun SignUpScreen(modifier: Modifier = Modifier)
{
    val authViewModel: AuthViewModel = koinViewModel()
    val viewState = authViewModel.uiState.collectAsState().value

    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }

    val emailErrors = remember { mutableStateListOf<String>() }
    val usernameErrors  = remember { mutableStateListOf<String>() }
    val passwordErrors  = remember { mutableStateListOf<String>() }
    val passwordConfirmationErrors  = remember { mutableStateListOf<String>() }
    val hasErrors = remember { mutableStateOf(false) }

    if (viewState is UiState.Loading) {
        LoadingView(modifier.height(100.dp))
    }

    if (viewState is UiState.Error && !hasErrors.value ) {
        viewState.errors?.forEach { (index, fieldMessages) ->
            when (index) {
                "email" -> emailErrors.addAll(fieldMessages)
                "name" -> usernameErrors.addAll(fieldMessages)
                "password" -> passwordErrors.addAll(fieldMessages)
                "password_confirmation" -> passwordConfirmationErrors.addAll(fieldMessages)
            }
        }
        hasErrors.value = true
    }
    SignUpView(
        modifier = modifier,
        email = email,
        username = username,
        password = password,
        passwordConfirmation = passwordConfirmation,
        onUsernameChange = { username = it },
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onPasswordConfirmationChange = { passwordConfirmation = it },
        emailErrors = emailErrors,
        usernameErrors = usernameErrors,
        passwordErrors = passwordErrors,
        passwordConfirmationErrors = passwordConfirmationErrors,
        submitSignUp = {
            authViewModel.signUp(
                email,
                username,
                password,
                passwordConfirmation
            )
            emailErrors.clear()
            usernameErrors.clear()
            passwordErrors.clear()
            passwordConfirmationErrors.clear()
            hasErrors.value = false
        }
    )
}
