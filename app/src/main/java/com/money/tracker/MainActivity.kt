package com.money.tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.money.tracker.ui.navigation.Screen
import com.money.tracker.ui.screens.CurrencySelectionScreen
import com.money.tracker.ui.screens.GreetingScreen
import com.money.tracker.ui.screens.MainScreen
import com.money.tracker.ui.screens.NameInputScreen
import com.money.tracker.ui.theme.MoneyTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            currentComposer
            MoneyTrackerTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Greeting.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Greeting.route) {
                            GreetingScreen(navController = navController)
                        }
                        composable(Screen.NameInput.route) {
                            NameInputScreen(navController = navController)
                        }
                        composable(Screen.CurrencySelection.route) {
                            CurrencySelectionScreen(navController = navController)
                        }
                        composable(Screen.Main.route) {
                            MainScreen(navController = navController)
                        }
                    }
                }
            }
        }

    }
}