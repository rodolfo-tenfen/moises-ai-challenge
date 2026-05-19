package tenfen.rodolfo.data.itunes.music.repository

import tenfen.rodolfo.data.itunes.music.repository.port.MusicDataGateway
import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Song
import tenfen.rodolfo.domain.music.usecase.port.MusicGateway
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort.SongPage

internal class MusicRepository(private val musicDataGateway: MusicDataGateway) : MusicGateway {

    override suspend fun searchSongs(query: String, pageKey: SongPage.Key?): SongPage =
        musicDataGateway.search(query, pageKey)

    override suspend fun getAlbumSongs(albumId: Album.Id): List<Song> =
        musicDataGateway.getAlbumSongs(albumId)
}
