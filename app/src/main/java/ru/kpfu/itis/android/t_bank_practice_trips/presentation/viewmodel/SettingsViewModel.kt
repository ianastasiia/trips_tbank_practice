package ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Settings
import ru.kpfu.itis.android.t_bank_practice_trips.domain.usecase.GetSettingsUseCase
import ru.kpfu.itis.android.t_bank_practice_trips.domain.usecase.UpdateLanguageUseCase
import ru.kpfu.itis.android.t_bank_practice_trips.domain.usecase.UpdateThemeUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateThemeUseCase: UpdateThemeUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val settings = getSettingsUseCase()
            _uiState.value = SettingsState(
                settings = settings.copy(
                    language = when (settings.language) {
                        "Русский" -> "ru"
                        "English" -> "en"
                        else -> settings.language
                    }
                )
            )
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                settings = getSettingsUseCase()
            )
        }
    }

    fun onThemeChanged(isEnabled: Boolean) {
        viewModelScope.launch {
            updateThemeUseCase(isEnabled)
            _uiState.value = _uiState.value.copy(
                settings = _uiState.value.settings.copy(isDarkTheme = isEnabled)
            )
        }
    }

    fun onLanguageChanged(language: String) {
        viewModelScope.launch {
            updateLanguageUseCase(language)
            _uiState.value = _uiState.value.copy(
                settings = _uiState.value.settings.copy(language = language)
            )
        }
    }
}

data class SettingsState(
    val settings: Settings = Settings(false, "Русский")
)