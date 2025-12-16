package wingoritm.mobile.recall.core.designSystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import recall.composeapp.generated.resources.Res
import recall.composeapp.generated.resources.acme_regular
import recall.composeapp.generated.resources.dm_sans_regular

/**
 * 'Font' is a Composable function in KMP that loads the resource
*/
@Composable
fun getBodyFontFamily(): FontFamily {
    val regular = Font(Res.font.dm_sans_regular, FontWeight.Normal)

    return FontFamily(regular)
}
@Composable
fun getDisplayFontFamily(): FontFamily {
    val regular = Font(Res.font.acme_regular, FontWeight.Normal)
    return FontFamily(regular)
}

@Composable
fun getAppTypography(): Typography {
    val bodyFontFamily = getBodyFontFamily()
    val displayFontFamily = getDisplayFontFamily()

    val baseline = Typography()

    return Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
        bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
        labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
        labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
    )
}