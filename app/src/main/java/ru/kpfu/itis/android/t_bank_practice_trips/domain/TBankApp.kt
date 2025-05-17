package ru.kpfu.itis.android.t_bank_practice_trips.domain

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TBankApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupTheme()
    }

    fun updateConfiguration(config: Configuration) {
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun setupTheme() {
        val prefs = getSharedPreferences("app_settings", MODE_PRIVATE)
        val isDark = prefs.getBoolean("is_dark_theme", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}