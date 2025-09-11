package com.money.tracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.money.tracker.ui.navigation.Screen
import com.money.tracker.ui.screens.AppNavigationHost
import com.money.tracker.ui.theme.MoneyTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var isLoadingGreetingStatus by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            isLoadingGreetingStatus
        }

        enableEdgeToEdge()
        setContent {
            MoneyTrackerTheme {
                val navController = rememberNavController()
                val greetingCompleted by mainViewModel.greetingCompleted.collectAsStateWithLifecycle()

                greetingCompleted?.let {
                    isLoadingGreetingStatus = false
                }


                if (!isLoadingGreetingStatus) {
                    val startDestination = if (greetingCompleted == true) {
                        Screen.Main.route
                    } else {
                        Screen.Greeting.route
                    }

                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        AppNavigationHost(
                            navController = navController,
                            startDestination = Screen.CurrencySelection.route,
                            modifier = Modifier.padding(innerPadding),
                            mainViewModel = mainViewModel
                        )
                    }
                }
            }
        }
    }
}
