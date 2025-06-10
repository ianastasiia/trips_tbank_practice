package ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses

import java.time.LocalDate

data class Expense(
    val id: Long,
    val tripId: Long,
    val title: String,
    val category: ExpenseCategory,
    val amount: Double,
    val ownerId: Long,
    val createdAt: LocalDate,
    val paidForUserIds: List<Long>
)
