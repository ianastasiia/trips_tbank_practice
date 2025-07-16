package ru.kpfu.itis.android.t_bank_practice_trips.domain.repository

import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.User
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.Expense

interface TripExpensesRepository {
    suspend fun getTripParticipants(tripId: Long): List<User>
    suspend fun addExpense(tripId: Long, expense: Expense)
}