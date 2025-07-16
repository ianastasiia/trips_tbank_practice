package ru.kpfu.itis.android.t_bank_practice_trips.data.mapper

import ru.kpfu.itis.android.t_bank_practice_trips.data.request.ExpenseRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.ExpenseResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.Expense
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.ExpenseCategory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExpenseMapper @Inject constructor() {
    fun map(response: ExpenseResponse): Expense {
        return Expense(
            id = response.id,
            tripId = response.tripId,
            title = response.title,
            category = mapCategory(response.category),
            amount = response.amount,
            ownerId = response.ownerId,
            createdAt = parseDate(response.createdAt),
            paidForUserIds = response.paidForUserIds
        )
    }

    private fun mapCategory(category: String): ExpenseCategory {
        return ExpenseCategory.valueOf(category.uppercase())
    }

    private fun parseDate(dateString: String): LocalDate {
        val formatter = DateTimeFormatter.ISO_DATE
        return LocalDate.parse(dateString, formatter)
    }
}

fun Expense.toRequest() = ExpenseRequest(
    title = title,
    category = category.name,
    amount = amount,
//    ownerId = ownerId,
    paidForUserIds = paidForUserIds,
    createdAt = createdAt.toString()
)