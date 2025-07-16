package ru.kpfu.itis.android.t_bank_practice_trips.data.request

import com.google.gson.annotations.SerializedName

data class ExpenseRequest(
    @SerializedName("title") val title: String,
    @SerializedName("category") val category: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("paidForUserIds") val paidForUserIds: List<Long>,
    @SerializedName("createdAt") val createdAt: String
)