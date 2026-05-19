package tenfen.rodolfo.moisesaichallenge.presentation.album.details.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Artist
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.viewmodel.AlbumDetailsUiState
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.viewmodel.AlbumDetailsUiState.Error as ErrorState
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.viewmodel.AlbumDetailsUiState.Initial as InitialState
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.viewmodel.AlbumDetailsUiState.Loading as LoadingState
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.viewmodel.AlbumDetailsUiState.Success as SuccessState
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.viewmodel.AlbumDetailsViewModel
import tenfen.rodolfo.moisesaichallenge.presentation.song.item.SongItem
import tenfen.rodolfo.moisesaichallenge.presentation.theme.MoisesAIChallengeTheme

private const val TAG = "[AlbumDetailsScreen]"

@Composable
internal fun AlbumDetailsScreen(
    serializedAlbumId: String,
    albumName: String,
    onNavigateUp: () -> Unit,
    viewModel: AlbumDetailsViewModel = koinViewModel { parametersOf(serializedAlbumId) },
) {
    LaunchedEffect(Unit) {
        viewModel.load()
    }

    val state by viewModel.uiState.collectAsState()

    AlbumDetailsScreenView(state, albumName, onNavigateUp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumDetailsScreenView(
    state: AlbumDetailsUiState,
    albumName: String,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(albumName, style = MaterialTheme.typography.titleSmall) },
                navigationIcon = { NavigateUpButton(onNavigateUp) },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (state) {
                InitialState -> Unit
                LoadingState -> LoadingView()
                is SuccessState ->
                    SuccessView(
                        state,
                        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    )
                ErrorState -> ErrorView()
            }
        }
    }
}

@Composable
internal fun NavigateUpButton(onNavigateUp: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onNavigateUp, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Navigate up", // FIXME
        )
    }
}

@Composable
private fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

// TODO
@Composable
private fun ErrorView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Error") // FIXME
    }
}

@Composable
private fun SuccessView(state: SuccessState, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AlbumHeader(state.album, state.artist)
            }
        }

        item {
            Spacer(modifier = Modifier.size(40.dp))
        }

        items(state.songs.size) { i ->
            SongItem(state.songs[i], albumImageSize = 44.dp)
        }
    }
}

@Composable
private fun AlbumHeader(album: Album, artist: Artist, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AlbumHeaderImage(album.imageUrl)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(album.name, style = MaterialTheme.typography.titleMedium)

            Text(artist.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun AlbumHeaderImage(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier.size(120.dp).clip(RoundedCornerShape(20.dp)),
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center,
    )
}

@Preview
@Composable
private fun AlbumHeaderPreview() {
    MoisesAIChallengeTheme {
        AlbumHeader(
            album = object : Album {
                override val id = object : Album.Id {}
                override val name = "Album Title"
                override val imageUrl = "www.example.com"
            },
            artist = object : Artist {
                override val name = "Artist Name"
            },
        )
    }
}
