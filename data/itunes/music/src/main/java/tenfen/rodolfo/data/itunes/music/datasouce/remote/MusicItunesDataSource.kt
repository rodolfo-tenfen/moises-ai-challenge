package tenfen.rodolfo.data.itunes.music.datasouce.remote

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import tenfen.rodolfo.data.itunes.music.datasouce.remote.dto.SongBody
import tenfen.rodolfo.data.itunes.music.datasouce.remote.service.ItunesMusicPagingStrategy
import tenfen.rodolfo.data.itunes.music.datasouce.remote.service.ItunesMusicService
import tenfen.rodolfo.data.itunes.music.repository.port.MusicDataGateway
import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Song
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort.SongPage

internal class MusicItunesDataSource(
    private val service: ItunesMusicService,
    private val pagingStrategy: ItunesMusicPagingStrategy,
    private val songFactory: (SongBody) -> Song,
    private val pageFactory: (List<Song>, nextOffset: UInt?) -> SongPage,
    private val json: Json,
) : MusicDataGateway {

    override suspend fun search(query: String, pageKey: SongPage.Key?): SongPage {
        if (pageKey !is ItunesMusicPagingStrategy.OffsetKey?)
            throw IllegalArgumentException("The iTunes API only supports offset-based page keys")

        val (songs, nextOffset) = pagingStrategy.searchSongs(service, query, pageKey)

        return pageFactory.invoke(songs.map(songFactory), nextOffset)
    }

    override suspend fun getAlbumSongs(albumId: Album.Id): List<Song> {
        if (albumId !is ItunesAlbumId)
            throw IllegalArgumentException("The iTunes API only supports number-based album keys")

        return service
            .lookupSongsForAlbum(albumId.value)
            .results
            .drop(1)
            .map<JsonObject, SongBody>(json::decodeFromJsonElement)
            .map(songFactory)
    }

    @JvmInline
    value class ItunesAlbumId(val value: Long) : Album.Id {

        override fun toString() = value.toString()
    }
}
