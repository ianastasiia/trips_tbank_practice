package ru.kpfu.itis.android.t_bank_practice_trips.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("phone") val phone: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("hashPassword") val hashPassword: String,
    @SerializedName("status") val status: String = "ACTIVE",
    @SerializedName("role") val role: String = "USER",
    @SerializedName("active") val active: Boolean = true
)