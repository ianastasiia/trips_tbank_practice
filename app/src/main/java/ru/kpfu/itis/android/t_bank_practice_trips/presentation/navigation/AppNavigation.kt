package ru.kpfu.itis.android.t_bank_practice_trips.presentation.navigation

import MainScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.notifications.NotificationsScreen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.settings.SettingsScreen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.SettingsViewModel
import ru.kpfu.itis.android.tbank_design_system.theme.AppTheme

sealed class Screen(val route: String) {
    data object Trips : Screen("trips")
    data object Notifications : Screen("notifications")
    data object Settings : Screen("settings")
}

@Composable
fun AppNavigation(viewModel: SettingsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    AppTheme(darkTheme = state.settings.isDarkTheme) {
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
}