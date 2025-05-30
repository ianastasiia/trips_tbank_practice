package ru.kpfu.itis.android.t_bank_practice_trips.data.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("login") val login: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String?
)
