package ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.User
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.Expense
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.ExpenseCategory
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.TripExpensesRepository
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val repository: TripExpensesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tripId: Long = savedStateHandle.get<Long>("tripId") ?: 0L
    private val _state = MutableStateFlow(AddExpenseState())
    val state = _state.asStateFlow()

    init {
        loadParticipants()
    }

    private fun loadParticipants() {
        viewModelScope.launch {
            try {
                val participants = repository.getTripParticipants(tripId)
                _state.value = _state.value.copy(
                    participants = participants,
                    error = null,
                    selectedPayer = participants.firstOrNull()
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Не удалось загрузить участников: ${e.message}"
                )
            }
        }
    }

    fun setAmount(amount: String) {
        _state.value = _state.value.copy(amount = amount)
    }

    fun setCategory(category: ExpenseCategory) {
        _state.value = _state.value.copy(selectedCategory = category)
    }

    fun setPayer(payer: User) {
        _state.value = _state.value.copy(selectedPayer = payer)
    }

    fun togglePaidFor(participant: User) {
        val paidFor = _state.value.paidForParticipants.toMutableList()
        if (paidFor.contains(participant)) {
            paidFor.remove(participant)
        } else {
            paidFor.add(participant)
        }
        _state.value = _state.value.copy(paidForParticipants = paidFor)
    }

    fun setDate(date: LocalDate) {
        _state.value = _state.value.copy(selectedDate = date)
    }

    fun addExpense(onSuccess: () -> Unit) {
        val state = _state.value

        if (state.title.isBlank()) {
            _state.value = state.copy(error = "Введите название расхода")
            return
        }
        if (state.amount.isBlank() || state.amount.toDoubleOrNull() == null) {
            _state.value = state.copy(error = "Введите корректную сумму")
            return
        }
        if (state.selectedCategory == null) {
            _state.value = state.copy(error = "Выберите категорию")
            return
        }
        if (state.selectedPayer == null) {
            _state.value = state.copy(error = "Выберите плательщика")
            return
        }
        if (state.paidForParticipants.isEmpty()) {
            _state.value = state.copy(error = "Выберите участников")
            return
        }

        viewModelScope.launch {
            try {
                val expense = Expense(
                    id = 0,
                    tripId = tripId,
                    title = state.title,
                    category = state.selectedCategory!!,
                    amount = state.amount.toDouble(),
                    ownerId = state.selectedPayer!!.id,
                    createdAt = state.selectedDate,
                    paidForUserIds = state.paidForParticipants.map { it.id }
                )
                repository.addExpense(tripId, expense)
                onSuccess()
            } catch (e: Exception) {
                _state.value = state.copy(error = "Ошибка: ${e.message}")
            }
        }
    }

    fun setTitle(title: String) {
        _state.value = _state.value.copy(title = title)
    }
}

data class AddExpenseState(
    val title: String = "",
    val amount: String = "",
    val selectedCategory: ExpenseCategory? = null,
    val selectedPayer: User? = null,
    val paidForParticipants: List<User> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val participants: List<User> = emptyList(),
    val error: String? = null
)