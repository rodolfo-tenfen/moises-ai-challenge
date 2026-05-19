package tenfen.rodolfo.data.itunes.music.datasouce.remote.factory

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import tenfen.rodolfo.data.itunes.music.datasouce.remote.MusicItunesDataSource.ItunesAlbumId
import tenfen.rodolfo.data.itunes.music.datasouce.remote.dto.SongBody
import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Artist
import tenfen.rodolfo.domain.music.entity.Genre
import tenfen.rodolfo.domain.music.entity.Song

internal class MusicFactory {

    fun create(input: SongBody): Song = with(input) {
        OutputSong(
            id = OutputSong.Id(id),
            name = name,
            artist = OutputArtist(artistName),
            album = OutputAlbum(ItunesAlbumId(albumId), albumName, albumImageUrl),
            genre = OutputGenre(genreName),
            explicit = explicitness != "notExplicit",
            duration = durationMs.milliseconds,
        )
    }

    private data class OutputArtist(override val name: String) : Artist

    private data class OutputAlbum(
        override val id: Album.Id,
        override val name: String,
        override val imageUrl: String,
    ) : Album

    private data class OutputGenre(override val name: String) : Genre

    private data class OutputSong(
        override val id: Song.Id,
        override val name: String,
        override val artist: Artist,
        override val album: Album,
        override val genre: Genre,
        override val explicit: Boolean,
        override val duration: Duration,
    ) : Song {

        @JvmInline
        value class Id(val value: Long) : Song.Id
    }
}
