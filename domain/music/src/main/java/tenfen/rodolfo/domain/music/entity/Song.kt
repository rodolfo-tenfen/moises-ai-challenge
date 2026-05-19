package tenfen.rodolfo.domain.music.entity

import kotlin.time.Duration

interface Song {

    val id: Id

    val name: String

    val artist: Artist

    val album: Album

    val genre: Genre

    val explicit: Boolean

    val duration: Duration

    interface Id
}
