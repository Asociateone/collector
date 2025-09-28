package com.example.collecter.ui.composables.partials.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavbar (
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit = {}
) {
    CenterAlignedTopAppBar({
        Column {
            Text("?dasdnsadjdas", color = Color.Black)
        }
    }, modifier, navigationIcon = {
        IconButton(openDrawer) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
    })
}