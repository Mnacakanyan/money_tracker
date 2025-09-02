package com.money.tracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.money.tracker.ui.navigation.Screen
import com.money.tracker.ui.theme.MoneyTrackerTheme

@Composable
fun GreetingScreen(
    navController: NavController,
    onGreetingComplete: () -> Unit // Added callback
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Money Tracker!")
        Button(onClick = {
            onGreetingComplete() // Call the callback
            navController.navigate(Screen.NameInput.route)
        }) {
            Text("Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    MoneyTrackerTheme {
        GreetingScreen(
            navController = rememberNavController(),
            onGreetingComplete = {} // Provide a no-op for preview
        )
    }
}
