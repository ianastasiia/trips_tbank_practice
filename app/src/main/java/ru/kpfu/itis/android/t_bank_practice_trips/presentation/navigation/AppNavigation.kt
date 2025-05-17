package ru.kpfu.itis.android.t_bank_practice_trips.presentation.navigation

import MainScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.notifications.NotificationsScreen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.settings.SettingsScreen

sealed class Screen(val route: String) {
    object Trips : Screen("trips")
    object Notifications : Screen("notifications")
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Trips.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Trips.route) { MainScreen() }
            composable(Screen.Notifications.route) { NotificationsScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}