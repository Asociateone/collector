package com.example.collecter.ui.composables.views.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingView(modifier: Modifier = Modifier): Unit
{
    Box (
        modifier
            .fillMaxSize()
            .background((Color.Transparent)).blur(1.dp)
            .alpha(1f)

    ) {
            Text("Loading...")
    }
}