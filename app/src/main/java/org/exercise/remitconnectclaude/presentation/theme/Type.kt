package org.exercise.remitconnectclaude.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.exercise.remitconnectclaude.R

val outfitFamily = FontFamily(
    Font(R.font.outfit_thin, FontWeight.Thin),
    Font(R.font.outfit_extra_light, FontWeight.ExtraLight),
    Font(R.font.outfit_light, FontWeight.Light),
    Font(R.font.outfit_medium, FontWeight.Medium),
    Font(R.font.outfit_regular, FontWeight.Normal),
    Font(R.font.outfit_semi_bold, FontWeight.SemiBold),
    Font(R.font.outfit_bold, FontWeight.Bold),
    Font(R.font.outfit_extra_bold, FontWeight.ExtraBold),
    Font(R.font.outfit_black, FontWeight.Black),
)

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = outfitFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = outfitFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = outfitFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = outfitFamily),

    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = outfitFamily, fontWeight = FontWeight.Bold, lineHeight = 36.sp),

    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = outfitFamily, fontWeight = FontWeight.SemiBold, lineHeight = 36.sp),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = outfitFamily, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, lineHeight = 27.sp),

    titleMedium = defaultTypography.titleMedium.copy(fontFamily = outfitFamily, fontWeight = FontWeight.Normal, lineHeight = 24.sp),

    titleSmall = defaultTypography.titleSmall.copy(fontFamily = outfitFamily, fontWeight = FontWeight.Medium, lineHeight = 21.sp),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = outfitFamily, fontWeight = FontWeight.Medium, lineHeight = 24.sp),

    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = outfitFamily, fontWeight = FontWeight.Medium, lineHeight = 18.sp),

    bodySmall = defaultTypography.bodySmall.copy(fontFamily = outfitFamily, fontWeight = FontWeight.SemiBold, lineHeight = 18.sp),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = outfitFamily, fontWeight = FontWeight.SemiBold, lineHeight = 23.sp, fontSize = 18.sp),

    labelMedium = defaultTypography.labelMedium.copy(fontFamily = outfitFamily, fontWeight = FontWeight.Normal, lineHeight = 18.sp),

    labelSmall = defaultTypography.labelSmall.copy(fontFamily = outfitFamily)
)