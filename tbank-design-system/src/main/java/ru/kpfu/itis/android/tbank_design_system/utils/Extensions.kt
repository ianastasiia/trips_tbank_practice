package ru.kpfu.itis.android.tbank_design_system.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.appCardShape(cornerSize: Dp = 8.dp) = this.clip(
    RoundedCornerShape(cornerSize)
)

fun Modifier.appPadding(
    horizontal: Dp = 16.dp,
    vertical: Dp = 8.dp
) = this.padding(
    horizontal = horizontal,
    vertical = vertical
)