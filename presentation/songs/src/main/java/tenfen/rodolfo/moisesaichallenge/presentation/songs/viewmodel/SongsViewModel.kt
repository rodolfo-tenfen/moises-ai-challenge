package tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.update
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort
import tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel.SongsUiState.Content
import tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel.SongsUiState.Empty

internal class SongsViewModel(
    private val pagedSongSearchUseCase: PagedSongSearchInputPort,
    val pageSize: UInt,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SongsUiState>(SongsUiState.Initial)
    val uiState = _uiState.asStateFlow()

    private val immediateSearchTrigger = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val pagingConfiguration = PagingConfig(
        pageSize = pageSize.toInt(),
        initialLoadSize = pageSize.toInt(),
        prefetchDistance = (pageSize.toInt() * PREFETCH_THRESHOLD).toInt(),
    )

    fun start() = _uiState.update { Empty }

    fun onQueryChanged(query: String) {
        Log.d(TAG, "onQueryChanged: query=$query")

        _uiState.update {
            when {
                query.isBlank() -> Empty
                it is Content -> it.copy(query = query)
                else -> Content(query, createSongsFlow(), isLoading = false)
            }
        }
    }

    fun onSearchTriggered() {
        _uiState.value
            .let { it as? Content }
            ?.query
            .orEmpty()
            .let(immediateSearchTrigger::tryEmit)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun createSongsFlow() =
        merge(createSongsFlowFromQueryChanges(), immediateSearchTrigger)
            .map(String::trim)
            .distinctUntilChanged { old, new -> old == new }
            .flatMapLatest { query ->
                Log.d(TAG, "createSongsFlow: query=$query")

                _uiState.update {
                    if (it !is Content) return@update it

                    it.copy(isLoading = true)
                }

                Pager(
                    pagingConfiguration,
                    pagingSourceFactory = { SongsPagingSource(query, pagedSongSearchUseCase) },
                ).flow
            }
            .cachedIn(viewModelScope)

    @OptIn(FlowPreview::class)
    private fun createSongsFlowFromQueryChanges() =
        _uiState
            .mapNotNull { it as? Content }
            .map { it.query }
            .debounce(500)

    companion object {
        private const val TAG = "[SongsViewModel]"

        // FIXME move to application module
        private const val PREFETCH_THRESHOLD = 0.3
    }
}
