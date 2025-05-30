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
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.TripStatus
import ru.kpfu.itis.android.tbank_design_system.components.actions.CardItem
import ru.kpfu.itis.android.tbank_design_system.components.buttons.BaseButton
import ru.kpfu.itis.android.tbank_design_system.components.tabs.TabGroup
import ru.kpfu.itis.android.tbank_design_system.theme.AppTheme
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Column(modifier = Modifier.background(LocalExtendedColorScheme.current.base01)) {
                TabGroup(
                    items = listOf("Все", "Активные", "Завершенные"),
                    selectedIndex = selectedTab,
                    onItemSelected = { selectedTab = it }
                )
            }
        },
        floatingActionButton = {
            BaseButton(
                onClick = { /* Добавить поездку */ },
                icon = Icons.Default.Add,
                modifier = Modifier
                    .size(56.dp)
            )

        },
    ) { padding ->
        TripList(
            modifier = Modifier.padding(padding), trips = listOf(
                Trip(
                    "Шашлыки на даче у Олега Т.", "30.06.2024 - 13.07.2024", TripStatus.ACTIVE
                ),
                Trip(
                    "Отпуск в Сочи", "30.04.2025 - 13.05.2025", TripStatus.ACTIVE
                ),
                Trip(
                    "Путешествие в Санкт-Петербург", "05.09.2023 - 10.09.2023", TripStatus.COMPLETED
                ),
                Trip(
                    "Поездка в Берлин", "30.06.2023 - 07.07.2023", TripStatus.COMPLETED
                )
            )
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
        onClick = { /* Обработка клика */ }
    )
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}