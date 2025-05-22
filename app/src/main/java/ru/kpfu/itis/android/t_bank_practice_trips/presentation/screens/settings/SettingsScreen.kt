package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kpfu.itis.android.t_bank_practice_trips.R
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.AppLanguage
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.SettingsViewModel
import ru.kpfu.itis.android.tbank_design_system.components.dropdown.Dropdown
import ru.kpfu.itis.android.tbank_design_system.components.sections.settings.SettingsItem
import ru.kpfu.itis.android.tbank_design_system.components.sections.settings.SettingsSection
import ru.kpfu.itis.android.tbank_design_system.components.switches.Switch
import ru.kpfu.itis.android.tbank_design_system.theme.AppTheme
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val languageOptions = AppLanguage.getDisplayNames()


    AppTheme(darkTheme = state.settings.isDarkTheme) {
        Column(
            modifier = Modifier.fillMaxSize().padding(Dimensions.paddingLarge)
        ) {
            Text(
                text = stringResource(R.string.settings_title),
                style = AppTypography.titleLarge,
                color = LocalExtendedColorScheme.current.text01,
                modifier = Modifier.padding(bottom = Dimensions.paddingLarge)
            )
            SettingsSection {
                SettingsItem(title = stringResource(R.string.theme),
                    trailingContent = {
                    Switch(
                        checked = state.settings.isDarkTheme,
                        onCheckedChange = viewModel::onThemeChanged
                    )
                })

                SettingsItem(title = stringResource(R.string.language),
                    trailingContent = {
                    Dropdown(
                        selectedOption = state.settings.language,
                        options = languageOptions,
                        onOptionSelected = viewModel::onLanguageChanged
                    )
                })

            }
        }
    }
}