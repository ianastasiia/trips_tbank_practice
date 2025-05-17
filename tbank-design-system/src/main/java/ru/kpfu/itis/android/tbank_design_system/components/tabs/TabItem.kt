package ru.kpfu.itis.android.tbank_design_system.components.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions.Tabs.sizeSHeight
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

enum class TabSize(
    val minWidth: Dp = 0.dp,
    val height: Dp,
) {
    S(height = sizeSHeight)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabItem(
    text: String,
    isSelected: Boolean = false,
    size: TabSize = TabSize.S,
    onSelectChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) {
        LocalExtendedColorScheme.current.text01
//        MaterialTheme.colorScheme.primary
    } else {
        LocalExtendedColorScheme.current.base04
    }

    val borderWidth = if (isSelected) {
        Dimensions.borderWidthSelected
    } else {
        Dimensions.borderWidthDefault
    }

    FilterChip(
        selected = isSelected,
        onClick = { onSelectChanged(!isSelected) },
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
            )
        },
        shape = RoundedCornerShape(Dimensions.cornerRadiusMedium),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = borderColor,
            borderWidth = borderWidth,
            enabled = true,
            selected = isSelected
        ),
//        border = selectableChipBorder(
//            borderWidth = if (isSelected) Dimensions.borderWidthSelected else Dimensions.borderWidthDefault,
//            borderColor = LocalExtendedColorScheme.current.base04,
//            selected = isSelected
//            width = if (isSelected) Dimensions.borderWidthSelected else Dimensions.borderWidthDefault,
//            color = LocalExtendedColorScheme.current.base04
//        ),

//        colors = colors,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = LocalExtendedColorScheme.current.base01,
            selectedContainerColor = LocalExtendedColorScheme.current.base01
        ),
        modifier = modifier
//            .border(
//                width = if (isSelected) Dimensions.borderWidthSelected else Dimensions.borderWidthDefault,
//                color = LocalExtendedColorScheme.current.base04,
//                shape = RoundedCornerShape(Dimensions.cornerRadiusMedium)
//            )
            .padding(horizontal = Dimensions.paddingSmall)
            .wrapContentWidth()
    )

}