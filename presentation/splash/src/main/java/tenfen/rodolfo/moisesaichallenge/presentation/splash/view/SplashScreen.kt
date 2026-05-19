package tenfen.rodolfo.moisesaichallenge.presentation.splash.view

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.time.Duration
import org.koin.compose.koinInject
import org.koin.core.qualifier.named
import tenfen.rodolfo.moisesaichallenge.presentation.splash.R
import tenfen.rodolfo.moisesaichallenge.presentation.splash.di.SPLASH_SCREEN_ANIMATION_DURATION_DEPENDENCY_NAME
import tenfen.rodolfo.moisesaichallenge.presentation.theme.DarkTeal

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    val animationDuration: Duration = koinInject(
        named(SPLASH_SCREEN_ANIMATION_DURATION_DEPENDENCY_NAME),
    )

    val topRightColor = remember { Animatable(Color.Black) }

    LaunchedEffect(Unit) {
        topRightColor.animateTo(
            targetValue = DarkTeal,
            animationSpec = tween(durationMillis = animationDuration.inWholeMilliseconds.toInt()),
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(topRightColor.value, Color.Black),
                    start = Offset(Float.POSITIVE_INFINITY, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_musical_note_image),
            contentDescription = null,
            modifier = Modifier.size(173.dp),
        )
    }
}
