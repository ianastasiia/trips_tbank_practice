package ru.kpfu.itis.android.t_bank_practice_trips.data.repository

import ru.kpfu.itis.android.t_bank_practice_trips.data.api.TripApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.ExpenseMapper
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.TripMapper
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.TripStatus
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.Expense
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.TripRepository
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val api: TripApiService,
    private val mapper: TripMapper,
    private val expenseMapper: ExpenseMapper,
) : TripRepository {

    override suspend fun getTrips(status: TripStatus): List<Trip> =
        mapper.map(
            api.getTripsByStatus(
                status = status.name
            )
        )

    override suspend fun getTripById(tripId: Long): Trip {
        return mapper.map(api.getTripById(tripId))
    }

    override suspend fun getExpensesByTrip(tripId: Long): List<Expense> {
        return api.getExpensesByTrip(tripId).map { expenseMapper.map(it) }
    }
}