package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.trips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.kpfu.itis.android.t_bank_practice_trips.R
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.Expense
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.ExpenseCategory
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.navigation.Screen
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.CurrentTripViewModel
import ru.kpfu.itis.android.tbank_design_system.components.actions.CardItem
import ru.kpfu.itis.android.tbank_design_system.components.buttons.BaseButton
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentTripScreen(
    navController: NavController,
    tripId: Long,
    viewModel: CurrentTripViewModel = hiltViewModel()
) {
    LaunchedEffect(tripId) {
        viewModel.loadTripDetails(tripId)
    }

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back
                            )
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            BaseButton(
                onClick = {
                    navController.navigate(Screen.AddExpense.createRoute(tripId))
                },
                icon = Icons.Default.Add, modifier = Modifier.size(56.dp)
            )

        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            state.error?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(24.dp)
                )
            }

            state.trip?.let { trip ->
                Text(
                    text = trip.title,
                    modifier = Modifier.padding(Dimensions.paddingLarge)
                        .align(Alignment.CenterHorizontally),
                    style = AppTypography.titleLarge,
                    color = LocalExtendedColorScheme.current.text01,
                )

                ExpenseList(expenses = state.expenses)
            }
        }
    }
}

@Composable
fun ExpenseList(expenses: List<Expense>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(expenses) { expense ->
            ExpenseItem(expense = expense)
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    CardItem(
        title = "${expense.amount} ₽",
        descriptionText = "${expense.title} • ${getCategoryName(expense.category)}",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun getCategoryName(category: ExpenseCategory): String {
    return when (category) {
        ExpenseCategory.TICKETS -> stringResource(R.string.category_tickets)
        ExpenseCategory.LODGING -> stringResource(R.string.category_lodging)
        ExpenseCategory.FOOD -> stringResource(R.string.category_food)
        ExpenseCategory.ENTERTAINMENT -> stringResource(R.string.category_entertainment)
        ExpenseCategory.INSURANCE -> stringResource(R.string.category_insurance)
        ExpenseCategory.TRANSPORT -> stringResource(R.string.category_transport)
        ExpenseCategory.OTHER -> stringResource(R.string.category_other)
    }
}