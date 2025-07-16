package ru.kpfu.itis.android.t_bank_practice_trips.data.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: UserResponse
)
