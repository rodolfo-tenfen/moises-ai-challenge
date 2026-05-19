package tenfen.rodolfo.domain.music.usecase.port

import tenfen.rodolfo.domain.music.entity.Song

interface PagedSongSearchInputPort {

    suspend fun searchSongs(query: String, pageKey: SongPage.Key?): SongPage

    interface SongPage {
        val songs: List<Song>

        val nextKey: Key?

        interface Key
    }
}
