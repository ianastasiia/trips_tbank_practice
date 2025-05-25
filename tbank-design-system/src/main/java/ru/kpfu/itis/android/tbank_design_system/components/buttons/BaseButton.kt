package ru.kpfu.itis.android.tbank_design_system.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun BaseButton(
    text: String? = null,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier.wrapContentWidth(),
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    containerColor: Color = LocalExtendedColorScheme.current.yellow,
    contentColor: Color = LocalExtendedColorScheme.current.text01,
//    disabledContainerColor: Color = LocalExtendedColorScheme.current.base04,
//    disabledContentColor: Color = LocalExtendedColorScheme.current.text03
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
//            .wrapContentWidth(),
//            .padding(horizontal = 2.dp),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(Dimensions.cornerRadiusMedium),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.56F),
            disabledContentColor = contentColor.copy(alpha = 0.56F)
        ),
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(24.dp)
                )
//                Spacer(Modifier.width(Dimensions.paddingSmall))
            }

            text?.let {
                Text(
                    text = it,
                    style = AppTypography.bodySmall,
                    color = contentColor
                )
            }
        }
    }

}