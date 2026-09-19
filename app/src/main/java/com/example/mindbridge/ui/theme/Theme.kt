package com.example.mindbridge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = MindBridgeBlue,
    onPrimary = Color.White,
    primaryContainer = MindBridgeLightBlue,
    onPrimaryContainer = MindBridgeDarkBlue,
    secondary = MindBridgeGreen,
    onSecondary = Color.White,
    secondaryContainer = MindBridgeLightGreen,
    onSecondaryContainer = MindBridgeGreen,
    background = MindBridgeBackground,
    onBackground = MindBridgeNavy,
    surface = MindBridgeCardBg,
    onSurface = MindBridgeNavy,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = MindBridgeTextMuted,
    error = CrisisRed,
    onError = Color.White,
    errorContainer = CrisisRedBg,
    onErrorContainer = CrisisRed
)

private val DarkColorScheme = darkColorScheme(
    primary = MindBridgeBlue,
    onPrimary = Color.White,
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC)
)

@Composable
fun MindBridgeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
