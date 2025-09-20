package com.example.collecter.ui.composables.screens.auth

import android.R.id.message
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import org.koin.core.definition.indexKey
import kotlin.collections.emptyList

@Composable
public fun SignUpScreen(modifier: Modifier = Modifier)
{
    val authViewModel: AuthViewModel = koinViewModel()
    val viewState = authViewModel.uiState.collectAsState().value

    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String>("")}
    var usernameError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var passwordConfirmationError by remember { mutableStateOf("") }

    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (viewState is UiState.Error) {
            viewState.errors?.forEach{ index, message ->
                when (index) {
                    "email" -> emailError = message.toString()
                    "name" -> usernameError = message.toString()
                    "password" -> passwordError = message.toString()
                    "password_confirmation" -> passwordConfirmationError = message.toString()
                }
            }
        }
        TextInputField(email, {email = it}, Modifier, "Email", errorMessage = emailError)
        TextInputField(username, {username = it}, Modifier, "Naam", errorMessage = usernameError)
        TextInputField(password, {password = it}, Modifier, "Wachtwoord", isPassword = true, errorMessage = passwordError)
        TextInputField(passwordConfirmation, {passwordConfirmation = it}, Modifier, "Wachtwoord herhaling", isPassword = true, errorMessage = passwordConfirmationError)
        Button(Modifier, "Registreren", { authViewModel.signUp(
            email,
            username,
            password,
            passwordConfirmation
        ) })
    }
}