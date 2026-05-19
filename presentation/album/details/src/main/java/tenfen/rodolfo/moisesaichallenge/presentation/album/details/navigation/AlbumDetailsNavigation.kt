package tenfen.rodolfo.moisesaichallenge.presentation.album.details.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.view.AlbumDetailsScreen

@Serializable
data class AlbumDetailsRoute(val serializedAlbumId: String, val albumName: String)

fun NavGraphBuilder.albumDetailsRoute(navController: NavHostController) {
    composable<AlbumDetailsRoute> { entry ->
        AlbumDetailsRoute(
            serializedAlbumId = entry.toRoute<AlbumDetailsRoute>().serializedAlbumId,
            albumName = entry.toRoute<AlbumDetailsRoute>().albumName,
            navController::navigateUp,
        )
    }
}

fun NavHostController.navigateToAlbumDetails(serializedAlbumId: String, albumName: String) {
    navigate(AlbumDetailsRoute(serializedAlbumId = serializedAlbumId, albumName = albumName))
}

@Composable
private fun AlbumDetailsRoute(
    serializedAlbumId: String,
    albumName: String,
    onNavigateUp: () -> Unit,
) {
    AlbumDetailsScreen(serializedAlbumId = serializedAlbumId, albumName = albumName, onNavigateUp)
}
