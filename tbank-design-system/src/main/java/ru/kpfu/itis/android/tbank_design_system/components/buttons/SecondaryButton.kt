package ru.kpfu.itis.android.tbank_design_system.components.buttons

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun SecondaryButton(
    text: String? = null,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
//    isLoading: Boolean = false,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {

    BaseButton(
        text = text,
        icon = icon,
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
//        isLoading = isLoading,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = LocalExtendedColorScheme.current.link,
    )
}