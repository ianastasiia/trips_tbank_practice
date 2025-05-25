package ru.kpfu.itis.android.tbank_design_system.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtendedColorScheme(
    val base01: Color,
    val base04: Color,
    val base06: Color,
    val base07: Color,
    val text01: Color,
    val text02: Color,
    val text03: Color,
    val elevation01: Color,
    val link: Color,
    val errorFill: Color,
    val yellow: Color,
    val primaryActive: Color
    )

val LocalExtendedColorScheme = staticCompositionLocalOf {
    ExtendedColorScheme(
        base01 = Color.Unspecified,
        base04 = Color.Unspecified,
        base06 = Color.Unspecified,
        base07 = Color.Unspecified,
        text01 = Color.Unspecified,
        text02 = Color.Unspecified,
        text03 = Color.Unspecified,
        link = Color.Blue,
        elevation01 = Color.Unspecified,
        errorFill = Color.Red,
        yellow = Color.Yellow,
        primaryActive = Color.Blue
    )
}

val LightExtendedColors = ExtendedColorScheme(
    base01 = Color(0xFFFFFFFF), // Day/Base/base-01
    base04 = Color(0xFFE0E0E0), // Day/Base/base-04
    base06 = Color(0xFF959595), // Day/Base/base-06
    base07 = Color(0xFF808080), // Day/Base/base-07
    text01 = Color(0xFF191C30),  // Day/Text & Icons/text-01
    text02 = Color(0x651B1F3B),  // Day/Text & Icons/text-02
    text03 = Color(0x401B1F3B),  // Day/Text & Icons/text-03
    link = Color(0xFF526ED3),  // Day/Text & Icons/link
    elevation01 = Color(0xFFFFFFFF), // Day/Base/elevation01
    errorFill = Color(0xFFF45725), // Day/Status/error-fill
    yellow = Color(0xFFFFDD2D),
    primaryActive = Color(0xFF314692)
)

val DarkExtendedColors = ExtendedColorScheme(
    base01 = Color(0xFF121212), // Night/Base/base-01
    base04 = Color(0xFF2C2C2E), // Night/Base/base-04
    base06 = Color(0xFFC7C9CC), // Night/Base/base-06
    base07 = Color(0xFFDDDFE0), // Night/Base/base-07
    text01 = Color(0xFFFFFFFF), // Night/Text/text-01
    text02 = Color(0x72FFFFFF), // Night/Text/text-02
    text03 = Color(0x60FFFFFF),  // Night/Text/text-03
    link = Color(0xFF6788FF),  // Night/Text/link
    elevation01 = Color(0xFF202020),
    errorFill = Color(0xFFFF8C67), // Night/Status/error-fill
    yellow = Color(0xFFFFDD2D),
    primaryActive = Color(0xFF314692)
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF526ED3), // Day/Base/primary
//    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFFFFF), // Day/Base/base-01
//    onPrimaryContainer = Color(0xFF21005D),
    secondary = Color(0xFFF4F4F4),
//    onSecondary = Color(0xFFFFFFFF),
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
//    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF121212), // Night/Base/base-01
//    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0x206488B4),
//    onSecondary = Color(0xFF332D41),
)
