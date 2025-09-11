package com.money.tracker.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.money.tracker.ui.MainViewModel
import com.money.tracker.ui.navigation.Screen

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
            OnboardingSetCurrency(
                navController = navController,
                preselectedCurrency = Currency.getDefault(),
                onSetCurrency = {}
            )
        }
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }


    }
}