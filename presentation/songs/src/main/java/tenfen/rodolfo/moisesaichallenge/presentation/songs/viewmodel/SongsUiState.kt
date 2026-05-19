package tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import tenfen.rodolfo.domain.music.entity.Song

sealed interface SongsUiState {

    data object Initial : SongsUiState

    data class Content(
        val query: String,
        val songs: Flow<PagingData<Song>>,
        val isLoading: Boolean,
    ) : SongsUiState

    data object Empty : SongsUiState

    data object Error : SongsUiState
}
