package tenfen.rodolfo.domain.music.usecase

import tenfen.rodolfo.domain.music.usecase.port.MusicGateway
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort.SongPage

internal class PagedSongSearchUseCase(val musicGateway: MusicGateway) : PagedSongSearchInputPort {

    override suspend fun searchSongs(query: String, pageKey: SongPage.Key?): SongPage =
        musicGateway.searchSongs(query, pageKey)
}
