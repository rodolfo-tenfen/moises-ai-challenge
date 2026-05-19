package tenfen.rodolfo.moisesaichallenge.presentation.song.item

import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Artist
import tenfen.rodolfo.domain.music.entity.Genre
import tenfen.rodolfo.domain.music.entity.Song

internal val previewSong = object : Song {
    override val id = object : Song.Id {}
    override val name = "Purple Rain"
    override val artist = object : Artist {
        override val name = "Prince"
    }
    override val album = object : Album {
        override val id = object : Album.Id {}
        override val name = "Purple Rain"
        override val imageUrl = "www.example.com"
    }
    override val genre = object : Genre {
        override val name = "R&B"
    }
    override val explicit = false
    override val duration = 8.minutes + 45.seconds
}
