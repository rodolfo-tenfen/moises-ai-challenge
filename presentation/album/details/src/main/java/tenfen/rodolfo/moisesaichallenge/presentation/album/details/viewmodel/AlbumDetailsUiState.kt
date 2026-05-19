package tenfen.rodolfo.moisesaichallenge.presentation.album.details.viewmodel

import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Artist
import tenfen.rodolfo.domain.music.entity.Song

sealed interface AlbumDetailsUiState {

    data object Initial : AlbumDetailsUiState

    data object Loading : AlbumDetailsUiState

    data class Success(val album: Album, val artist: Artist, val songs: List<Song>) :
        AlbumDetailsUiState

    data object Error : AlbumDetailsUiState
}
