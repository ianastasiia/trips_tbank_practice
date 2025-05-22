package ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.android.t_bank_practice_trips.domain.constants.AppConstants
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.AppLanguage.Companion.toLanguageCode
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.AppLanguage.Companion.toLanguageDisplayName
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Settings
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepo: SettingsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            val settings = settingsRepo.getSettings()
            withContext(Dispatchers.Main) {
                _uiState.value = SettingsState(
                    settings = settings.copy(
                        language = settings.language.toLanguageDisplayName()
                    )
                )
            }
        }
    }

    fun onThemeChanged(isEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.updateTheme(isEnabled)
            withContext(Dispatchers.Main) {
                _uiState.update { it.copy(settings = it.settings.copy(isDarkTheme = isEnabled)) }
            }
        }
    }

    fun onLanguageChanged(language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepo.updateLanguage(
                language.toLanguageCode()
            )
        }
    }
}

data class SettingsState(
    val settings: Settings = Settings(
        isDarkTheme = AppConstants.PREF_IS_THEME_DARK_VAL,
        language = AppConstants.PREF_LANGUAGE_DEF_VAL
    )
)