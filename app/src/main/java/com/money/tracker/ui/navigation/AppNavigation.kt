package com.money.tracker.ui.navigation

sealed class Screen(val route: String) {
    object Greeting : Screen("greeting_screen")
    object NameInput : Screen("name_input_screen")
    object CurrencySelection : Screen("currency_selection_screen")
    object Main : Screen("main_screen")
    object CSVScreen : Screen("csv_screen")
}
