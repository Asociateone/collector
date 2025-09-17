package com.example.collecter.ui.composables.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.collecter.ui.composables.partials.Button
import com.example.collecter.ui.composables.partials.formFields.TextInputField

@Composable
public fun SignUpScreen(modifier: Modifier = Modifier)
{
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextInputField("", {}, Modifier, "Email")
        TextInputField("", {}, Modifier, "Naam")
        TextInputField("", {}, Modifier, "Wachtwoord", isPassword = true)
        TextInputField("", {}, Modifier, "Wachtwoord herhaling", isPassword = true)
        Button(Modifier, "Registreren", {})
    }
}