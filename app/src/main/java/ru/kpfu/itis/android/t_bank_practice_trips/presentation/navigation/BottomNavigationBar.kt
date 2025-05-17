package ru.kpfu.itis.android.t_bank_practice_trips.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.kpfu.itis.android.t_bank_practice_trips.R
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Trips,
        Screen.Notifications,
        Screen.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        getIcon(screen),
                        contentDescription = null,
                        tint = if (currentRoute == screen.route) LocalExtendedColorScheme.current.primaryActive else LocalExtendedColorScheme.current.base07
                    )
                },
                label = {
                    Text(
                        getLabel(screen),
                        color = if (currentRoute == screen.route) LocalExtendedColorScheme.current.primaryActive else LocalExtendedColorScheme.current.base07
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

private fun getIcon(screen: Screen): ImageVector = when (screen) {
    Screen.Trips -> Icons.Default.LocationOn
    Screen.Notifications -> Icons.Default.Notifications
    Screen.Settings -> Icons.Default.Settings
}

@Composable
private fun getLabel(screen: Screen): String = when (screen) {
    Screen.Trips -> stringResource(R.string.trips_menu)
    Screen.Notifications -> stringResource(R.string.notifications_menu)
    Screen.Settings -> stringResource(R.string.settings_menu)
}