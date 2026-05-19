package tenfen.rodolfo.moisesaichallenge.presentation.song.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Song
import tenfen.rodolfo.moisesaichallenge.presentation.theme.MoisesAIChallengeTheme

@Composable
fun SongItem(
    song: Song,
    modifier: Modifier = Modifier,
    albumImageSize: Dp = 52.dp,
    onViewAlbumClicked: ((Album) -> Unit)? = null,
) {
    Row(
        modifier = modifier.wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 8.dp), // TODO .clickable(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AlbumImage(song.album.imageUrl, modifier = Modifier.size(albumImageSize))

        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            SongAndArtist(
                songName = song.name,
                artistName = song.artist.name,
                modifier = Modifier.weight(1f),
            )

            if (onViewAlbumClicked != null)
                ActionButton(song, onViewAlbumClicked)
        }
    }
}

@Composable
private fun AlbumImage(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.FillHeight,
        alignment = Alignment.Center,
    )
}

@Composable
private fun SongAndArtist(songName: String, artistName: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            4.dp,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        Text(songName, style = MaterialTheme.typography.bodyLarge)

        Text(artistName, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun ActionButton(
    song: Song,
    onViewAlbumClicked: (Album) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isSongActionSheetVisible by remember { mutableStateOf(false) }

    fun showSongActionSheet() {
        isSongActionSheetVisible = true
    }

    fun hideSongActionSheet() {
        isSongActionSheetVisible = false
    }

    IconButton(onClick = ::showSongActionSheet, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More options", // FIXME
            tint = MaterialTheme.colorScheme.surfaceVariant,
        )
    }

    if (isSongActionSheetVisible) {
        SongActionSheet(
            song,
            onDismissRequest = ::hideSongActionSheet,
            onViewAlbumClick = {
                onViewAlbumClicked(song.album)
                hideSongActionSheet()
            },
        )
    }
}

@Preview
@Composable
fun SongItemPreview() {
    MoisesAIChallengeTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SongItem(previewSong, onViewAlbumClicked = {})

            SongItem(previewSong)
        }
    }
}
