package ru.kpfu.itis.android.tbank_design_system.components.actions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItem(
    title: String,
    descriptionText: String? = null,
    image: Painter? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isSelected: Boolean = false,
) {
    val colors = LocalExtendedColorScheme.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.horizontalMargin),
        shape = RoundedCornerShape(Dimensions.cornerRadiusMedium),
        colors = CardDefaults.cardColors(
            containerColor = colors.elevation01,
            contentColor = colors.text01
        ),
        elevation = CardDefaults.cardElevation(Dimensions.paddingSmall),
        border = if (isSelected) BorderStroke(
            Dimensions.borderWidthSelected,
            MaterialTheme.colorScheme.primary
        ) else null,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(Dimensions.paddingLarge)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            image?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(Dimensions.paddingLarge))
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = AppTypography.bodyLarge,
                    color = colors.text01,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                descriptionText?.let {
                    Spacer(Modifier.height(Dimensions.paddingSmall))
                    Text(
                        text = it,
                        style = AppTypography.bodySmall,
                        color = colors.text02,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}