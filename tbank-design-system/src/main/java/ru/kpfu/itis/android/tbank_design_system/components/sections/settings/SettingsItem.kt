package ru.kpfu.itis.android.tbank_design_system.components.sections.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun SettingsItem(
    title: String,
    modifier: Modifier = Modifier,
    trailingContent: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.paddingMedium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = AppTypography.bodyLarge,
            color = LocalExtendedColorScheme.current.text01
        )
        trailingContent()
    }
}