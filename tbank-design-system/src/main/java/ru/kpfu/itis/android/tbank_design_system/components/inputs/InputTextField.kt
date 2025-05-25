package ru.kpfu.itis.android.tbank_design_system.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.android.tbank_design_system.R
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

enum class InputSize(val height: Dp) {
    L(56.dp), M(44.dp), S(32.dp)
}

enum class InputState {
    Normal, Hover, Active, Error, ErrorActive, ReadOnly,
}

@Composable
fun InputTextField(
    fieldLabel: String? = null,
    fieldValue: String,
    placeholder: String? = null,
    labelOutside: Boolean = false,
    isFilled: Boolean = true,
    onValueChanged: (String) -> Unit,
    state: InputState = InputState.Normal,
    sizes: InputSize = InputSize.M,
    modifier: Modifier = Modifier,
    showCloseIcon: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    val baseColors = MaterialTheme.colorScheme
    val colors = LocalExtendedColorScheme.current
    val cornerRadius = Dimensions.cornerRadiusMedium

    val containerColor = when {
        !isFilled -> Color.Transparent
        state == InputState.ReadOnly -> colors.base04
        else -> colors.base01
    }

    val borderColor = when (state) {
        InputState.Active -> baseColors.primary
        InputState.Error -> colors.errorFill
        InputState.ErrorActive -> colors.errorFill
        else -> colors.base04
    }
    val borderWidth = when (state) {
        InputState.Active -> 2.dp
        InputState.Error -> 2.dp
        else -> 1.dp
    }

    Column(modifier = modifier.width(320.dp)) {
        if (!labelOutside && fieldLabel != null) {
            Text(
                text = fieldLabel,
                style = AppTypography.bodySmall,
                color = colors.text02,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        Row(
            modifier = Modifier
                .height(sizes.height)
                .clip(RoundedCornerShape(cornerRadius))
                .background(containerColor)
                .border(borderWidth, borderColor, RoundedCornerShape(cornerRadius))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.invoke()

            BasicTextField(
                value = fieldValue,
                onValueChange = onValueChanged,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                textStyle = AppTypography.bodyMedium.merge(
                    TextStyle(color = colors.text01)
                ),
                enabled = state != InputState.ReadOnly,
                decorationBox = { innerTextField ->
                    if (fieldValue.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = AppTypography.bodyMedium,
                            color = colors.text02
                        )
                    }
                    innerTextField()
                }
            )

            if (showCloseIcon) {
                IconButton(
                    onClick = { onValueChanged("") },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close_24),
                        contentDescription = "Clear",
                        tint = colors.text03
                    )
                }
            }
            trailingIcon?.invoke()
        }

    }

}