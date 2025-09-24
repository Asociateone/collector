package com.example.collecter.ui.composables.views.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.collecter.ui.composables.partials.Button
import com.example.collecter.ui.composables.partials.formFields.TextInputField

@Composable
fun SignUpView(
    modifier: Modifier = Modifier,
    email: String,
    username: String,
    password: String,
    passwordConfirmation: String,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    submitSignUp: () -> Unit,
    emailErrors: List<String> = emptyList(),
    usernameErrors: List<String> = emptyList(),
    passwordErrors: List<String> = emptyList(),
    passwordConfirmationErrors: List<String> = emptyList(),
    isLoading: Boolean = false
)
{
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextInputField(email, onEmailChange, Modifier, "Email", errorMessages = emailErrors, isDisabled = isLoading)
        TextInputField(username, onUsernameChange, Modifier, "Naam", errorMessages = usernameErrors, isDisabled = isLoading)
        TextInputField(password, onPasswordChange, Modifier, "Wachtwoord", isPassword = true, errorMessages = passwordErrors, isDisabled = isLoading)
        TextInputField(passwordConfirmation, onPasswordConfirmationChange, Modifier, "Wachtwoord herhaling", isPassword = true, errorMessages = passwordConfirmationErrors, isDisabled = isLoading)

        Button(
            modifier = Modifier,
            value = "Registreren",
            onClick = submitSignUp,
            enabled = !isLoading
        )
    }
}