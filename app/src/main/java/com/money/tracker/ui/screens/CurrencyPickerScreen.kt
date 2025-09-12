package com.money.tracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.money.tracker.ui.common.GradientCutBottom
import com.money.tracker.ui.common.OnboardingButton
import com.money.tracker.ui.screens.picker.CurrencyPicker

@Composable
fun OnboardingSetCurrency(
    navController: NavController,
    preselectedCurrency: Currency,
    onSetCurrency: (Currency) -> Unit
) {
    var currency by remember { mutableStateOf(preselectedCurrency) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        Spacer(Modifier.height(16.dp))
        var keyboardVisible by remember { mutableStateOf(false) }

        IconButton(
            modifier = Modifier.padding(start = 20.dp),
            onClick = { navController.popBackStack() }
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        if (!keyboardVisible) {
            Spacer(Modifier.height(24.dp))
            Text(
                modifier = Modifier.padding(horizontal = 32.dp),
                text = "Set Currency",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black
                )
            )
        }

        Spacer(Modifier.height(24.dp))

        CurrencyPicker(
            modifier = Modifier.fillMaxSize(),
            initialSelectedCurrency = null,
            preselectedCurrency = preselectedCurrency,
            includeKeyboardShownInsetSpacer = true,
            lastItemSpacer = 120.dp,
            onKeyboardShown = { keyboardShown ->
                keyboardVisible = keyboardShown
            }
        ) {
            currency = it
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        GradientCutBottom(
            modifier = Modifier.align(Alignment.BottomCenter),
            height = 160.dp
        )

        OnboardingButton(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .navigationBarsPadding()
                .padding(bottom = 20.dp),
            text = "Set",
            textColor = Color.White,
            hasNext = true,
            enabled = true
        ) {
            onSetCurrency(currency)
        }
    }
}
