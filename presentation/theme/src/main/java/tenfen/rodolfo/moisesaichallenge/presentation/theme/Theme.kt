package tenfen.rodolfo.moisesaichallenge.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    background = Black,
    onBackground = Color.White,
    surface = Black,
    onSurface = Color.White,
    surfaceVariant = DarkGray,
    onSurfaceVariant = LightGray,
    outline = VeryLightGray,
)

@Composable
fun MoisesAIChallengeTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content,
    )
}
