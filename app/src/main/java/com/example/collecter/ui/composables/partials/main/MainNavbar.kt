package com.example.collecter.ui.composables.partials.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MainNavbar (modifier: Modifier = Modifier) {
    NavigationBar (modifier = modifier) {
        Column {
            Text("?dasdnsadjdas", color = Color.Black)
        }
    }
}