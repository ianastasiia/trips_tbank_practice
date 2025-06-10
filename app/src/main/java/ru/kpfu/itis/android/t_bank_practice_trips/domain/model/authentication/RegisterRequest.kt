package ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication

data class RegisterRequest (
    val name: String,
    val surname: String? = null,
    val phone: String,
    val password: String,
)
