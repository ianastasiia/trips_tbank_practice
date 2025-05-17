package ru.kpfu.itis.android.t_bank_practice_trips.data.repository

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Settings
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.SettingsRepository
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    @ActivityContext private val activityContext: Context
) : SettingsRepository {

    private val prefs = appContext.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    override suspend fun getSettings(): Settings {
        return Settings(
            isDarkTheme = prefs.getBoolean("is_dark_theme", false),
            language = prefs.getString("language", "ru") ?: "ru"
        )
    }

    override suspend fun updateTheme(isEnabled: Boolean) {
        prefs.edit().putBoolean("is_dark_theme", isEnabled).apply()
        AppCompatDelegate.setDefaultNightMode(
            if (isEnabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override suspend fun updateLanguage(language: String) {
        prefs.edit().putString("language", language).apply()
        updateAppLocale(language)
    }

    private fun updateAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration().apply {
            setLocale(locale)
        }

        appContext.resources.updateConfiguration(config, appContext.resources.displayMetrics)

        if (activityContext is Activity) {
            activityContext.recreate()
        }
    }
}