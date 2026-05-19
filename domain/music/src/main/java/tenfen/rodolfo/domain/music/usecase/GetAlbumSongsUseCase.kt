package tenfen.rodolfo.domain.music.usecase

import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.entity.Song
import tenfen.rodolfo.domain.music.usecase.port.GetAlbumSongsInputPort
import tenfen.rodolfo.domain.music.usecase.port.MusicGateway

internal class GetAlbumSongsUseCase(val musicGateway: MusicGateway) : GetAlbumSongsInputPort {

    override suspend fun getAlbumSongs(albumId: Album.Id): List<Song> =
        musicGateway.getAlbumSongs(albumId)
}
