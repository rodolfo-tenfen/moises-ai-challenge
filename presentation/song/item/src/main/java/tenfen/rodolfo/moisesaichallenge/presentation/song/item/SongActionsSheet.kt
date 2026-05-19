package tenfen.rodolfo.moisesaichallenge.presentation.song.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tenfen.rodolfo.domain.music.entity.Song
import tenfen.rodolfo.moisesaichallenge.presentation.theme.MoisesAIChallengeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SongActionSheet(
    song: Song,
    onDismissRequest: () -> Unit,
    onViewAlbumClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier,
    ) {
        SongActionSheetContent(song, onViewAlbumClick, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun SongActionSheetContent(
    song: Song,
    onViewAlbumClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp).padding(top = 12.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = song.name,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
            )

            Text(
                text = song.artist.name,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onViewAlbumClick() }
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.songs_album_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )

            Text(
                text = "View album", // FIXME
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
private fun SongActionSheetPreview() {
    MoisesAIChallengeTheme {
        SongActionSheetContent(previewSong, onViewAlbumClick = {})
    }
}
