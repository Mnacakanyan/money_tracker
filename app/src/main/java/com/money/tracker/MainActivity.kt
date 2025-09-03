package com.money.tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.money.tracker.ui.MainViewModel
import com.money.tracker.ui.navigation.Screen
import com.money.tracker.ui.screens.CurrencySelectionScreen
import com.money.tracker.ui.screens.GreetingScreen
import com.money.tracker.ui.screens.MainScreen
import com.money.tracker.ui.screens.NameInputScreen
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
                            startDestination = startDestination,
                            modifier = Modifier.padding(innerPadding),
                            mainViewModel = mainViewModel
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun AppNavigationHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Greeting.route) {
            GreetingScreen(
                navController = navController,
                onGreetingComplete = { mainViewModel.setGreetingCompleted(true) }
            )
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
