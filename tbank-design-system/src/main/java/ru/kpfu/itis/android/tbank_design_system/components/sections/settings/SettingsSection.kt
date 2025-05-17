package ru.kpfu.itis.android.tbank_design_system.components.sections.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.kpfu.itis.android.tbank_design_system.theme.AppShapes
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun SettingsSection(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
//        shape = AppShapes.medium,
        color = LocalExtendedColorScheme.current.base01,
//        shadowElevation = Dimensions.elevationSmall
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimensions.paddingSmall)
        ) {
            content()
        }
    }
}
