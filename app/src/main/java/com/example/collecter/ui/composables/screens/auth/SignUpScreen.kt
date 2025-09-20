package com.example.collecter.ui.composables.screens.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.collecter.enums.UiState
import com.example.collecter.ui.composables.partials.Button
import com.example.collecter.ui.composables.partials.formFields.TextInputField
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
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextInputField(email, {email = it}, Modifier, "Email", errorMessages = emailErrors)
        TextInputField(username, {username = it}, Modifier, "Naam", errorMessages = usernameErrors)
        TextInputField(password, {password = it}, Modifier, "Wachtwoord", isPassword = true, errorMessages = passwordErrors)
        TextInputField(passwordConfirmation, {passwordConfirmation = it}, Modifier, "Wachtwoord herhaling", isPassword = true, errorMessages = passwordConfirmationErrors)

        Button(
            modifier = Modifier,
            value = "Registreren",
            onClick = {
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
}
