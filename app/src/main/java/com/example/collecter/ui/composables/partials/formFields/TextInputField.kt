package com.example.collecter.ui.composables.partials.formFields

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Placeholder",
    isPassword: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    isDisabled: Boolean = false,
    errorMessages: List<String> = emptyList<String>()
)
{
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    val borderColor = if (isFocused) Color.LightGray else Color.Transparent
    val borderWidth = if (isFocused) 1.dp else 0.dp

    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            enabled = !isDisabled,
            modifier = Modifier // Removed fillMaxWidth from here
                .padding(10.dp)
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = ShapeDefaults.Medium
                )
                .padding(14.dp)
                .fillMaxWidth(), // Added fillMaxWidth here to take the width of the parent Column
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            keyboardOptions = KeyboardOptions(imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() },
                onSearch = { focusManager.clearFocus() },
                onGo = { focusManager.clearFocus() },
                onSend = { focusManager.clearFocus() },
                onNext = { focusManager.clearFocus() }
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholderText,
                            fontSize = 28.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                    innerTextField()
                }
            }
        )
        errorMessages.forEach { errorMessage ->
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TextInputFieldPreview()
{
    TextInputField(value = "", onValueChange = {}, placeholderText = "Enter text here")
}

@Composable
@Preview(showBackground = true)
fun TextInputFieldWithTextPreview()
{
    TextInputField(value = "Hello", onValueChange = {}, placeholderText = "Enter text here")
}

@Composable
@Preview(showBackground = true)
fun TextInputFieldErrorPreview()
{
    TextInputField(value = "test@example.com", onValueChange = {}, placeholderText = "Enter text here")
}
