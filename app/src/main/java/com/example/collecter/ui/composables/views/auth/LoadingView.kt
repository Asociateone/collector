package com.example.collecter.ui.composables.views.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingView(modifier: Modifier = Modifier): Unit
{
    Box(modifier.fillMaxSize().background(Color.DarkGray.copy(alpha = 0.5f)).alpha(0.5f)) {
            Text("Loading...")
    }
}