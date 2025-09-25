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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.collecter.enums.AuthNavigation
import com.example.collecter.ui.composables.partials.AuthenticationNavBar
import com.example.collecter.ui.models.AuthViewModel
import com.example.collecter.ui.navigations.AuthenticationNavigation
import com.example.collecter.ui.theme.CollecterTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CollecterTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    if (navBackStackEntry?.destination?.route.toString() !== AuthNavigation.SignIn.name) {
                        AuthenticationNavBar(Modifier.padding(innerPadding).padding(5.dp), navController)
                    }
                    Column(modifier = Modifier.padding(innerPadding)) {
                        val authViewModel: AuthViewModel = koinViewModel()

                        val token = authViewModel.getToken().collectAsStateWithLifecycle(null)

                        if (token.value === null) {
                            AuthenticationNavigation(modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                                navController
                            )
                        } else {
                            Text(text = "Token: $token") // Display the token (optional)
                        }
                    }
                }
            }
        }
    }
}