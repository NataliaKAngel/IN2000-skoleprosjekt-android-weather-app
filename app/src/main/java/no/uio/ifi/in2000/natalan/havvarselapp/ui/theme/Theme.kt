package no.uio.ifi.in2000.natalan.havvarselapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import no.uio.ifi.in2000.natalan.havvarselapp.R

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun HavvarselAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}



// Font we use in app
val InterFontFamily = FontFamily(Font(R.font.inter_font))

// Typography we use in app
val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.05).sp,
        color = TextColor
    ),
    displayMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.05).sp,
        color = TextColor
    ),
    displaySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (-0.05).sp,
        color = TextColor
    ),

    headlineLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (-0.05).sp,
        color = TextColor
    ),
    headlineMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextColor
    ),
    headlineSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = TextColor
    ),


    bodyLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = TextColor
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = TextColor
    ),
    bodySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = TextColor
    ),

    labelLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        color = BlueSignature
    ),
    labelSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        color = ActionBlue
    )
)