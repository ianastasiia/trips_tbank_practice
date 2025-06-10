package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.trips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import ru.kpfu.itis.android.t_bank_practice_trips.R
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.navigation.Screen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.MainScreenViewModel
import ru.kpfu.itis.android.tbank_design_system.components.actions.CardItem
import ru.kpfu.itis.android.tbank_design_system.components.buttons.BaseButton
import ru.kpfu.itis.android.tbank_design_system.components.tabs.TabGroup
import ru.kpfu.itis.android.tbank_design_system.theme.AppTheme
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(selectedTab) {
        viewModel.loadTrips(selectedTab)
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Column(modifier = Modifier.background(LocalExtendedColorScheme.current.base01)) {
                TabGroup(
                    items = listOf(
                        stringResource(R.string.all),
                        stringResource(R.string.active),
                        stringResource(R.string.completed)
                    ),
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

        when {
            state.isLoading -> LoadingIndicator()
            state.error != null -> ErrorMessage(state.error)
            else ->
                TripList(
                    modifier = Modifier.padding(padding),
                    trips = state.trips,
                    navController = navController,
                )
        }
    }
}

@Composable
fun TripList(
    modifier: Modifier = Modifier,
    trips: List<Trip>,
    navController: NavController,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = Dimensions.paddingMedium)
    ) {
        items(trips) { trip ->
            TripCard(
                trip = trip,
                onClick = { navController.navigate(Screen.CurrentTrip.createRoute(trip.id)) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TripCard(
    trip: Trip,
    onClick: () -> Unit,
) {
    CardItem(
        title = trip.title,
        descriptionText = trip.dateRange,
        modifier = Modifier.padding(horizontal = 16.dp),
        onClick = onClick,
    )
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage(error: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = error ?: "Произошла ошибка", color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(navController = rememberNavController())
    }
}