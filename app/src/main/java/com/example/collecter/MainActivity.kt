package com.example.collecter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.collecter.ui.models.AuthViewModel
import com.example.collecter.ui.navigations.AuthenticationNavigation
import com.example.collecter.ui.navigations.MainNavigation
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = koinViewModel()
                val token = authViewModel.getToken().collectAsStateWithLifecycle(null)

                if (token.value === null) {
                    AuthenticationNavigation(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        navController
                    )
                } else {
                    MainNavigation(
                        modifier = Modifier.padding(innerPadding),
                        navController
                    )
                }
            }
        }
    }
}