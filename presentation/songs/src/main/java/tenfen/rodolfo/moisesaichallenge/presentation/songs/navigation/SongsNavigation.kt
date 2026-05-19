package tenfen.rodolfo.moisesaichallenge.presentation.songs.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.navigation.albumDetailsRoute
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.navigation.navigateToAlbumDetails
import tenfen.rodolfo.moisesaichallenge.presentation.songs.view.SongsScreen

@Serializable
object SongsRoute

fun NavGraphBuilder.songsRoute() {
    composable<SongsRoute> {
        SongsRoute()
    }
}

@Composable
private fun SongsRoute() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = SongsRoute) {
        songsContentRoute(navController)

        albumDetailsRoute(navController)
    }
}

private fun NavGraphBuilder.songsContentRoute(navController: NavHostController) {
    composable<SongsRoute> {
        SongsScreen(
            onViewAlbumClicked = { album ->
                navController.navigateToAlbumDetails(
                    serializedAlbumId = album.id.toString(),
                    albumName = album.name,
                )
            },
        )
    }
}
