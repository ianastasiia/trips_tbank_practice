package ru.kpfu.itis.android.t_bank_practice_trips

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.SettingsRepository
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.navigation.AppNavigation
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var settingsRepo: SettingsRepository

    private var languageJob: Job? = null
    private var themeJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            applyInitialSettings()
            setupObservers()
            setContent { AppNavigation() }
        }
    }

    private suspend fun applyInitialSettings() {
        val settings = settingsRepo.getSettings()
        applyAppTheme(settings.isDarkTheme)
        updateLocale(settings.language)
    }

    private fun applyAppTheme(isDarkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun setupObservers() {
        setupLanguageObserver()
        setupThemeObserver()
    }

    private fun setupLanguageObserver() {
        languageJob = lifecycleScope.launch {
            settingsRepo.languageUpdates
                .flowWithLifecycle(lifecycle)
                .collect { langCode ->
                    updateLocale(langCode)
                    recreateActivity()
                }
        }
    }

    private fun setupThemeObserver() {
        themeJob = lifecycleScope.launch {
            settingsRepo.themeUpdates
                .flowWithLifecycle(lifecycle)
                .collect { _ ->
                    recreateActivity()
                }
        }
    }

    private fun updateLocale(langCode: String) {
        val locale = Locale(langCode)
        val config = Configuration(resources.configuration).apply {
            setLocale(locale)
            setLayoutDirection(locale)
        }
//        applicationContext.createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun recreateActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        languageJob?.cancel()
        themeJob?.cancel()
    }
}