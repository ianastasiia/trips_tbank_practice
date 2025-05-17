package ru.kpfu.itis.android.tbank_design_system.components.switches

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = LocalExtendedColorScheme.current.base01,
            checkedTrackColor = MaterialTheme.colorScheme.primary,

            uncheckedThumbColor = LocalExtendedColorScheme.current.base01,
            uncheckedTrackColor = MaterialTheme.colorScheme.secondary,
            uncheckedBorderColor = MaterialTheme.colorScheme.secondary
        )
    )
}