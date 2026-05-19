package tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import tenfen.rodolfo.domain.music.entity.Song
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort.SongPage

class SongsPagingSource(val query: String, val useCase: PagedSongSearchInputPort) :
    PagingSource<SongPage.Key, Song>() {

    override suspend fun load(params: LoadParams<SongPage.Key>): LoadResult<SongPage.Key, Song> {
        Log.d(TAG, "load: params = $params, this=${hashCode()}")

        Log.d(TAG, "load: params.key=${params.key}, params.loadSize=${params.loadSize}")

        val songPage = useCase.searchSongs(query, pageKey = params.key)

        Log.d(TAG, "load: songPage=$songPage")

        return LoadResult.Page(data = songPage.songs, prevKey = null, nextKey = songPage.nextKey)
    }

    override fun getRefreshKey(state: PagingState<SongPage.Key, Song>): SongPage.Key? = null

    companion object {
        private const val TAG = "[SongsPagingSource]"
    }
}
