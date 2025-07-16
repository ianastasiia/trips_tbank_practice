package ru.kpfu.itis.android.t_bank_practice_trips.data.repository

import ru.kpfu.itis.android.t_bank_practice_trips.data.api.TripExpensesApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.AuthMapper
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.toRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.User
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.Expense
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.TripExpensesRepository
import javax.inject.Inject

class TripExpensesRepositoryImpl @Inject constructor(
    private val api: TripExpensesApiService,
    private val authMapper: AuthMapper
) : TripExpensesRepository {

    override suspend fun getTripParticipants(tripId: Long): List<User> {
        return api.getTripParticipants(tripId).map { authMapper.map(it) }
    }

    override suspend fun addExpense(tripId: Long, expense: Expense) {
        api.addExpense(tripId, expense.toRequest())
    }
}