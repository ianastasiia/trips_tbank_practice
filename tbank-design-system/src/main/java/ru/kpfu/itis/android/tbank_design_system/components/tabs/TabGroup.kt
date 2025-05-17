package ru.kpfu.itis.android.tbank_design_system.components.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions

@Composable
fun TabGroup(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.horizontalMargin),
        horizontalArrangement = Arrangement.spacedBy(
            space = Dimensions.paddingMedium,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, text ->
            TabItem(
                text = text,
                isSelected = index == selectedIndex,
                onSelectChanged = { if (it) onItemSelected(index) },
                modifier = Modifier
            )
        }
    }

}