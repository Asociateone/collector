package com.example.collecter.ui.composables.views.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collecter.ui.composables.partials.Button
import com.example.collecter.ui.composables.partials.formFields.TextInputField

@Composable
fun SignInView(
    modifier: Modifier,
    email: String = "",
    password: String = "",
    errorMessage: String = "",
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    signIn: () -> Unit,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(errorMessage, color = Color.Red, fontSize = 22.sp)
        TextInputField(
            email,
            onEmailChange,
            Modifier, placeholderText = "E-mail",
            imeAction = ImeAction.Next,
            isDisabled = isLoading
        )
        TextInputField(password, onPasswordChange, Modifier, placeholderText = "Password", true, isDisabled = isLoading)
        AuthButton(Modifier, signIn, {}, isLoading = isLoading)
    }
}

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    signIn: () -> Unit,
    signUp: () -> Unit,
    isLoading: Boolean
)
{
    Row (
        modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
    ){
        Button(value = "Sign Up", onClick = signUp, enabled = !isLoading)
        Button(value = "Sign In", onClick = signIn, enabled = !isLoading)
    }
}

@Composable
@Preview(showBackground = true)
fun SignInViewPreview()
{
    SignInView(modifier = Modifier.fillMaxSize().padding(20.dp), "email", "password", "", {}, {}, {})
}

@Composable
@Preview
fun AuthButtonPreview()
{
    AuthButton(signIn = {}, signUp = {}, isLoading = false)
}