package ru.kpfu.itis.android.t_bank_practice_trips.domain

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.SettingsRepository
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class TBankApp : Application() {
    @Inject
    lateinit var settingsRepository: SettingsRepository

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            setupInitialConfiguration()
        }
    }

    private suspend fun setupInitialConfiguration() {
        val settings = settingsRepository.getSettings()
        applyTheme(settings.isDarkTheme)
        applyLocale(settings.language)
    }

    private fun applyTheme(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) MODE_NIGHT_YES else MODE_NIGHT_NO
        )
    }

    private fun applyLocale(langCode: String) {
        val config = Configuration(resources.configuration).apply {
            setLocale(Locale(langCode))
        }
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}