package tenfen.rodolfo.moisesaichallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlin.time.Duration
import kotlinx.coroutines.delay
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import tenfen.rodolfo.moisesaichallenge.presentation.songs.navigation.SongsRoute
import tenfen.rodolfo.moisesaichallenge.presentation.songs.navigation.songsRoute
import tenfen.rodolfo.moisesaichallenge.presentation.splash.di.SPLASH_SCREEN_ANIMATION_DURATION_DEPENDENCY_NAME
import tenfen.rodolfo.moisesaichallenge.presentation.splash.view.SplashScreen
import tenfen.rodolfo.moisesaichallenge.presentation.theme.MoisesAIChallengeTheme

internal class MoisesAIChallengeActivity : ComponentActivity() {

    private val splashAnimationDuration: Duration = get(
        named(SPLASH_SCREEN_ANIMATION_DURATION_DEPENDENCY_NAME),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setOnExitAnimationListener { view -> view.remove() }

        enableEdgeToEdge()

        setContent {
            var isSplashVisible by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(splashAnimationDuration.inWholeMilliseconds)

                isSplashVisible = false
            }

            MoisesAIChallengeContent(isSplashVisible)
        }
    }
}

@Composable
private fun MoisesAIChallengeContent(isSplashVisible: Boolean) {
    MoisesAIChallengeTheme {
        if (isSplashVisible) {
            SplashScreen()
        } else {
            NavHost(navController = rememberNavController(), startDestination = SongsRoute) {
                songsRoute()
            }
        }
    }
}
