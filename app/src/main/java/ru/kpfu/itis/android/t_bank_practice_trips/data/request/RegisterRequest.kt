package ru.kpfu.itis.android.t_bank_practice_trips.data.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("firstName")val firstName: String,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("hashPassword") val hashPassword: String,
    @SerializedName("status") val status: String = "ACTIVE",
    @SerializedName("role") val role: String = "USER",
    @SerializedName("active") val active: Boolean = true
)
