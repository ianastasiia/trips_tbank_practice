package ru.kpfu.itis.android.tbank_design_system.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(vertical = Dimensions.paddingSmall)
        ) {
            Text(
                text = selectedOption,
                style = AppTypography.bodyLarge,
                color = LocalExtendedColorScheme.current.text01
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = LocalExtendedColorScheme.current.text02,
                modifier = Modifier.padding(start = Dimensions.paddingSmall)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(LocalExtendedColorScheme.current.base01)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style = AppTypography.bodyLarge,
                            color = LocalExtendedColorScheme.current.text01
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }

//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded }
//    ) {
//        TextField(
//            modifier = Modifier.menuAnchor(),
//            readOnly = true,
//            value = selectedOption,
//            onValueChange = {},
//            textStyle = AppTypography.bodyLarge.copy(color = LocalExtendedColorScheme.current.text01),
//            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//            colors = ExposedDropdownMenuDefaults.textFieldColors(
//                focusedContainerColor  = LocalExtendedColorScheme.current.base01,
//                unfocusedContainerColor  = LocalExtendedColorScheme.current.base01,
//                focusedTextColor = LocalExtendedColorScheme.current.text01,
//                unfocusedTextColor = LocalExtendedColorScheme.current.text01,
//                focusedTrailingIconColor = LocalExtendedColorScheme.current.text02,
//                unfocusedTrailingIconColor = LocalExtendedColorScheme.current.text02
//            )
//        )
//
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier.background(LocalExtendedColorScheme.current.base01)
//        ) {
//            options.forEach { option ->
//                DropdownMenuItem(
//                    text = {
//                        Text(
//                            text = option,
//                            style = AppTypography.bodyLarge,
//                            color = LocalExtendedColorScheme.current.text01
//                        )
//                    },
//                    onClick = {
//                        onOptionSelected(option)
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
    }
}