package com.example.collecter.ui.composables.partials.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.collecter.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavbar (
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit = {},
    title: String = ""
) {
    CenterAlignedTopAppBar({
        Column {
            Text(title)
        }
    }, modifier,
        actions = {
            IconButton(openDrawer) {
                Icon(painterResource(R.drawable.menu), contentDescription = "Back")
            }
    }, )
}