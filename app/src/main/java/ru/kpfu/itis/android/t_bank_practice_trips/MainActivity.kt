package ru.kpfu.itis.android.t_bank_practice_trips

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.navigation.AppNavigation
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.SettingsViewModel
import ru.kpfu.itis.android.tbank_design_system.theme.AppTheme
import java.util.Locale

// MainActivity.kt
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SettingsViewModel by viewModels()
    private var isFirstLaunch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        applyAppTheme()
        super.onCreate(savedInstanceState)
        setContent { AppNavigation() }
        observeLanguageChanges()
    }

    private fun applyAppTheme() {
        val prefs = getSharedPreferences("app_settings", MODE_PRIVATE)
        val isDark = prefs.getBoolean("is_dark_theme", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun observeLanguageChanges() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (isFirstLaunch) {
                    isFirstLaunch = false
                    return@collect
                }
                if (state.settings.language != getCurrentLanguage()) {
                    recreate()
                }
            }
        }
    }

    private fun getCurrentLanguage(): String {
        val config = resources.configuration
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locales[0].language
        } else {
            @Suppress("DEPRECATION")
            config.locale.language
        }
    }
}