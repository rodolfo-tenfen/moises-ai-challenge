package tenfen.rodolfo.moisesaichallenge.presentation.album.details.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.usecase.port.GetAlbumSongsInputPort

class AlbumDetailsViewModel(
    private val albumId: Album.Id,
    private val getAlbumSongsUseCase: GetAlbumSongsInputPort,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.d(TAG, "error: $e")

        _uiState.value = AlbumDetailsUiState.Error
    }

    private val _uiState = MutableStateFlow<AlbumDetailsUiState>(AlbumDetailsUiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun load() {
        _uiState.update { AlbumDetailsUiState.Loading }

        viewModelScope.launch(exceptionHandler) {
            _uiState.update {
                with(getAlbumSongsUseCase.getAlbumSongs(albumId)) {
                    AlbumDetailsUiState.Success(
                        album = first().album,
                        artist = first().artist,
                        songs = this,
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "[AlbumDetailsViewModel]"
    }
}
