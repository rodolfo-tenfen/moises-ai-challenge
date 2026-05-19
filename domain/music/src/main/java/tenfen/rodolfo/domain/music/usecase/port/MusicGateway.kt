package tenfen.rodolfo.domain.music.usecase.port

import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Song
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort.SongPage

interface MusicGateway {

    suspend fun searchSongs(query: String, pageKey: SongPage.Key?): SongPage

    suspend fun getAlbumSongs(albumId: Album.Id): List<Song>
}
