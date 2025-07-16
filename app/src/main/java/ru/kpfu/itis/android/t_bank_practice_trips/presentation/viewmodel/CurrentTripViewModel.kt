package ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.Expense
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.TripRepository
import javax.inject.Inject

@HiltViewModel
class CurrentTripViewModel @Inject constructor(
    private val tripRepository: TripRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TripDetailsState())
    val state: StateFlow<TripDetailsState> = _state

    fun loadTripDetails(tripId: Long) {
        viewModelScope.launch {
            _state.value = TripDetailsState(isLoading = true)
            try {
                val trip = tripRepository.getTripById(tripId)
                val expenses = tripRepository.getExpensesByTrip(tripId)

                _state.value = TripDetailsState(
                    trip = trip,
                    expenses = expenses
                )
            } catch (e: Exception) {
                _state.value = TripDetailsState(
                    error = "Ошибка загрузки: ${e.message ?: "Unknown error"}"
                )
            }
        }
    }

     fun loadExpenses(tripId: Long) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isExpensesLoading = true)
            try {
                val expenses = tripRepository.getExpensesByTrip(tripId)
                _state.value = _state.value.copy(
                    expenses = expenses,
                    isExpensesLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to load expenses",
                    isExpensesLoading = false
                )
            }
        }
    }
}

data class TripDetailsState(
    val trip: Trip? = null,
    val expenses: List<Expense> = emptyList(),
    val isLoading: Boolean = false,
    val isExpensesLoading: Boolean = false,
    val error: String? = null
)