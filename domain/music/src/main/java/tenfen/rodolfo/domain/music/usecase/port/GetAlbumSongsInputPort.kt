package tenfen.rodolfo.domain.music.usecase.port

import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Song

interface GetAlbumSongsInputPort {

    suspend fun getAlbumSongs(albumId: Album.Id): List<Song>
}
