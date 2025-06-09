package ru.kpfu.itis.android.t_bank_practice_trips.data.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("phone") val phone: String,
//    @SerializedName("password") val password: String,
)
