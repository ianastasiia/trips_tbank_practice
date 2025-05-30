package ru.kpfu.itis.android.tbank_design_system.components.inputs

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun TuiTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    labelOutside: Boolean = true,
    state: InputState = InputState.Normal,
    minHeight: Dp = 132.dp,
    isFilled: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val colors = LocalExtendedColorScheme.current
    val baseColors = MaterialTheme.colorScheme

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

    Column(modifier = modifier.fillMaxWidth()) {
        if (labelOutside && label != null) {
            Text(
                text = label,
                style = AppTypography.bodySmall,
                color = colors.text02,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
                .background(containerColor, RoundedCornerShape(Dimensions.cornerRadiusMedium))
                .border(borderWidth, borderColor, RoundedCornerShape(Dimensions.cornerRadiusMedium))
                .padding(
                    PaddingValues(
                        horizontal = Dimensions.paddingLarge,
                        vertical = Dimensions.paddingMedium
                    )
                ),
            textStyle = AppTypography.bodyMedium.merge(
                TextStyle(color = colors.text01)
            ),
            enabled = enabled && !readOnly,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(baseColors.primary),
            decorationBox = { innerTextField ->
                if (value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = AppTypography.bodyMedium,
                        color = colors.text02
                    )
                }
                innerTextField()
            }
        )

        if (!labelOutside && label != null) {
            Text(
                text = label,
                style = AppTypography.bodySmall,
                color = colors.text02,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}