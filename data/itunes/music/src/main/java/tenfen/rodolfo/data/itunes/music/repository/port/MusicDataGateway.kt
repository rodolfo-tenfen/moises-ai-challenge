package tenfen.rodolfo.data.itunes.music.repository.port

import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Song
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort.SongPage

internal interface MusicDataGateway {

    suspend fun search(query: String, pageKey: SongPage.Key?): SongPage

    suspend fun getAlbumSongs(albumId: Album.Id): List<Song>
}
