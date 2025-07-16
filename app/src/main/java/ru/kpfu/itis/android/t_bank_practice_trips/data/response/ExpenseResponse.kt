package ru.kpfu.itis.android.t_bank_practice_trips.data.response

import com.google.gson.annotations.SerializedName

data class ExpenseResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("tripId") val tripId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("category") val category: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("ownerId") val ownerId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("paidForUserIds") val paidForUserIds: List<Long>,
)