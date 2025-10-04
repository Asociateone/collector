package com.example.collecter.ui.composables.partials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Button(modifier: Modifier = Modifier, value: String, onClick: () -> Unit, enabled: Boolean = true, isLoading: Boolean = false)
{
    TextButton(onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        colors = ButtonColors(
            containerColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.Gray,
            contentColor = Color.White
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            }
            Text(text = value, fontSize = 28.sp, modifier = Modifier.padding(15.dp, 10.dp))
        }
    }
}

@Composable
@Preview
fun ButtonPreview()
{
    Button(modifier = Modifier, value = "test", onClick = {})
}