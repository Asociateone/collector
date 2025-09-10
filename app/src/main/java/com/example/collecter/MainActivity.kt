package com.example.collecter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collecter.ui.composables.screens.auth.SignInScreen
import com.example.collecter.ui.models.AuthViewModel
import com.example.collecter.ui.theme.CollecterTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CollecterTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
//                        val authViewModel: AuthViewModel = koinViewModel()
//                        var token by remember { mutableStateOf<String?>(null) }
//
//                        LaunchedEffect(key1 = authViewModel) {
//                            token = authViewModel.getToken()
//                        }
//
//                        if (token === null) {
                            SignInScreen(
                                modifier = Modifier.fillMaxSize().padding(16.dp),
                            )
//                        } else {
//                            Text(text = "Token: $token") // Display the token (optional)
//                        }
                    }
                }
            }
        }
    }
}