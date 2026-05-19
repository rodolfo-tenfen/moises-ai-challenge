package tenfen.rodolfo.moisesaichallenge.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val dmSans = FontFamily(
    Font(R.font.dm_sans_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.dm_sans_medium, weight = FontWeight.Medium),
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = dmSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = (24 * 1.2).sp, // 120%
        letterSpacing = 0.sp,
        color = Color.White,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    titleMedium = TextStyle(
        fontFamily = dmSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = (20 * 1.2).sp, // 120%
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    titleSmall = TextStyle(
        fontFamily = dmSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = (18 * 1.08).sp, // 108%
        letterSpacing = 0.sp,
        color = Color.White,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    bodyLarge = TextStyle(
        fontFamily = dmSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = (16 * 1.2).sp, // 120%
        letterSpacing = 0.sp,
        color = Color.White,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    bodyMedium = TextStyle(
        fontFamily = dmSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = (14 * 1.2).sp, // 120%
        letterSpacing = 0.sp,
        color = Color.White,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    bodySmall = TextStyle(
        fontFamily = dmSans,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = (12 * 1.4).sp, // 140%
        letterSpacing = 0.sp,
        color = LightGray,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
)
