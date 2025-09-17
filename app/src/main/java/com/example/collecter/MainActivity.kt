package com.example.collecter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.collecter.enums.AuthNavigation
import com.example.collecter.ui.composables.partials.AuthenticationNavBar
import com.example.collecter.ui.navigations.AuthenticationNavigation
import com.example.collecter.ui.theme.CollecterTheme

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
//                        val authViewModel: AuthViewModel = koinViewModel()
//                        var token by remember { mutableStateOf<String?>(null) }
//
//                        LaunchedEffect(key1 = authViewModel) {
//                            token = authViewModel.getToken()
//                        }
//

//                        if (token === null) {
                        AuthenticationNavigation(modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                            navController
                        )
//                            SignInScreen(
//                                modifier = Modifier.fillMaxSize().padding(16.dp),
//                            )
//                        } el se {
//                            Text(text = "Token: $token") // Display the token (optional)
//                        }
                    }
                }
            }
        }
    }
}