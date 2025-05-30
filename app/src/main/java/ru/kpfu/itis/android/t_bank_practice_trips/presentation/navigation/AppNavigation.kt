package ru.kpfu.itis.android.t_bank_practice_trips.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.authentication.LoginScreen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.authentication.RegisterScreen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.notifications.NotificationsScreen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.settings.SettingsScreen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.trips.CreateTripScreen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.trips.MainScreen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.AuthViewModel
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.SettingsViewModel
import ru.kpfu.itis.android.tbank_design_system.theme.AppTheme

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Trips : Screen("trips")
    data object CreateTrip : Screen("create_trip")
    data object Notifications : Screen("notifications")
    data object Settings : Screen("settings")
}

@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val settingsState by settingsViewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState(false)

    LaunchedEffect(Unit) {
        authViewModel.checkAuthState()
    }

    AppTheme(darkTheme = settingsState.settings.isDarkTheme) {

        if (isAuthenticated) {
            Scaffold(
                bottomBar = { BottomNavigationBar(navController) }) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Trips.route,
                    modifier = Modifier.padding(padding)
                ) {
                    composable(Screen.Trips.route) { MainScreen(navController = navController) }
                    composable(Screen.CreateTrip.route) { CreateTripScreen(navController = navController) }
                    composable(Screen.Notifications.route) { NotificationsScreen() }
                    composable(Screen.Settings.route) { SettingsScreen() }
                }
            }
        } else {
            NavHost(
                navController = navController, startDestination = Screen.Login.route
            ) {
                composable(Screen.Login.route) {
                    LoginScreen(
                        navController = navController, onLoginSuccess = {
                            navController.navigate(Screen.Trips.route) {
                                popUpTo(0)
                            }
                        })
                }
                composable(Screen.Register.route) {
                    RegisterScreen(
                        navController = navController, onRegisterSuccess = {
                            navController.navigate(Screen.Trips.route) {
                                popUpTo(0)
                            }
                        })
                }
            }
        }
    }
}