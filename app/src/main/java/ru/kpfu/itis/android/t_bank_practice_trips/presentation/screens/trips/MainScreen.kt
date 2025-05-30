package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.trips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.TripStatus
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.navigation.Screen
import ru.kpfu.itis.android.tbank_design_system.components.actions.CardItem
import ru.kpfu.itis.android.tbank_design_system.components.buttons.BaseButton
import ru.kpfu.itis.android.tbank_design_system.components.tabs.TabGroup
import ru.kpfu.itis.android.tbank_design_system.theme.AppTheme
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun MainScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }

    val demoTrips = remember {
        listOf(
            Trip(
                id = "1",
                adminId = "admin123",
                title = "Шашлыки на даче у Олега Т.",
                startDate = "2024-06-30",
                endDate = "2024-07-13",
                status = TripStatus.ACTIVE,
                createdAt = "2024-05-15"
            ), Trip(
                id = "2",
                adminId = "admin123",
                title = "Отпуск в Сочи",
                startDate = "2025-04-30",
                endDate = "2025-05-13",
                status = TripStatus.ACTIVE,
                createdAt = "2024-05-10"
            ), Trip(
                id = "3",
                adminId = "admin123",
                title = "Путешествие в Санкт-Петербург",
                startDate = "2023-09-05",
                endDate = "2023-09-10",
                status = TripStatus.COMPLETED,
                createdAt = "2023-08-20"
            ), Trip(
                id = "4",
                adminId = "admin123",
                title = "Поездка в Берлин",
                startDate = "2023-06-30",
                endDate = "2023-07-07",
                status = TripStatus.COMPLETED,
                createdAt = "2023-05-15"
            )
        )
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Column(modifier = Modifier.background(LocalExtendedColorScheme.current.base01)) {
                TabGroup(
                    items = listOf("Все", "Активные", "Завершенные"),
                    selectedIndex = selectedTab,
                    onItemSelected = { selectedTab = it })
            }
        },
        floatingActionButton = {
            BaseButton(
                onClick = {
                    navController.navigate(Screen.CreateTrip.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                }, icon = Icons.Default.Add, modifier = Modifier.size(56.dp)
            )

        },
    ) { padding ->
        TripList(modifier = Modifier.padding(padding), trips = demoTrips.filter {
            when (selectedTab) {
                0 -> true
                1 -> it.status == TripStatus.ACTIVE
                2 -> it.status == TripStatus.COMPLETED
                else -> true
            }
        }
//            trips = listOf(
//                Trip("Шашлыки на даче у Олега Т.", "30.06.2024 - 13.07.2024", TripStatus.ACTIVE),

//                Trip(
//                    "Отпуск в Сочи", "30.04.2025 - 13.05.2025", TripStatus.ACTIVE
//                ),
//                Trip(
//                    "Путешествие в Санкт-Петербург", "05.09.2023 - 10.09.2023", TripStatus.COMPLETED
//                ),
//                Trip(
//                    "Поездка в Берлин", "30.06.2023 - 07.07.2023", TripStatus.COMPLETED
//                )
//            )
        )
    }
}

@Composable
fun TripList(modifier: Modifier = Modifier, trips: List<Trip>) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = Dimensions.paddingMedium)
    ) {
        items(trips) { trip ->
            TripCard(trip = trip)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TripCard(trip: Trip) {
    CardItem(
        title = trip.title,
        descriptionText = trip.dateRange,
        modifier = Modifier.padding(horizontal = 16.dp),
        onClick = { /* Обработка клика */ })
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(navController = rememberNavController())
    }
}