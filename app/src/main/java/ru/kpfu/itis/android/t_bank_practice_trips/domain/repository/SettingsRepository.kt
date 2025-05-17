package ru.kpfu.itis.android.t_bank_practice_trips.domain.repository

import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Settings

interface SettingsRepository {
    suspend fun getSettings(): Settings
    suspend fun updateTheme(isEnabled: Boolean)
    suspend fun updateLanguage(language: String)
}