package com.example.collecter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.collecter.ui.composables.partials.auth.AuthenticationScaffold
import com.example.collecter.ui.composables.partials.main.MainScaffold
import com.example.collecter.ui.models.AuthViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val authViewModel: AuthViewModel = koinViewModel()
            val navController = rememberNavController()
            val token = authViewModel.getToken().collectAsStateWithLifecycle(null)

            if (token.value === null) {
                AuthenticationScaffold(navController)
            } else {
                MainScaffold(navController)
            }
        }
    }
}