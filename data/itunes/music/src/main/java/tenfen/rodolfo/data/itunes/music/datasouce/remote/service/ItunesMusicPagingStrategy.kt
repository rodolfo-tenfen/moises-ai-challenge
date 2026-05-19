package tenfen.rodolfo.data.itunes.music.datasouce.remote.service

import tenfen.rodolfo.data.itunes.music.datasouce.remote.dto.SongBody
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort.SongPage

internal sealed interface ItunesMusicPagingStrategy {

    suspend fun searchSongs(
        musicService: ItunesMusicService,
        query: String,
        pageKey: OffsetKey?,
    ): Pair<List<SongBody>, UInt>

    class Offset(private val pageSize: UInt) : ItunesMusicPagingStrategy {

        override suspend fun searchSongs(
            musicService: ItunesMusicService,
            query: String,
            pageKey: OffsetKey?,
        ): Pair<List<SongBody>, UInt> {
            val offset = pageKey?.offset?.toInt() ?: 0
            val limit = pageSize.toInt()
            val nextOffset = offset + limit

            return musicService
                .search(term = query, offset = offset, limit = limit)
                .results to nextOffset.toUInt()
        }
    }

    class Discarding(private val pageSize: UInt) : ItunesMusicPagingStrategy {
        override suspend fun searchSongs(
            musicService: ItunesMusicService,
            query: String,
            pageKey: OffsetKey?,
        ): Pair<List<SongBody>, UInt> {
            val offset = pageKey?.offset?.toInt() ?: 0
            val limit = offset + pageSize.toInt()
            val nextOffset = limit.toUInt()

            return musicService
                .search(term = query, limit = limit)
                .results
                .drop(offset) to nextOffset
        }
    }

    interface OffsetKey : SongPage.Key {

        val offset: UInt
    }
}
