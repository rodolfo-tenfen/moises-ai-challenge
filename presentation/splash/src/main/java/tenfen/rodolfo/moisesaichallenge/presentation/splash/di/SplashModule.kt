package tenfen.rodolfo.moisesaichallenge.presentation.splash.di

import kotlin.time.Duration.Companion.seconds
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val SPLASH_SCREEN_ANIMATION_DURATION_DEPENDENCY_NAME = "SPLASH_SCREEN_ANIMATION_DURATION"

val presentationSplashModule = module {
    single(named(SPLASH_SCREEN_ANIMATION_DURATION_DEPENDENCY_NAME)) {
        1.seconds
    }
}
