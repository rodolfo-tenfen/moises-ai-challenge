package tenfen.rodolfo.data.itunes.music.datasouce.remote.factory

import tenfen.rodolfo.data.itunes.music.datasouce.remote.factory.PageFactory.OutputSongPage.OutputKey
import tenfen.rodolfo.data.itunes.music.datasouce.remote.service.ItunesMusicPagingStrategy
import tenfen.rodolfo.domain.music.entity.Song
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort.SongPage

internal class PageFactory {

    fun create(songs: List<Song>, nextOffset: UInt?): SongPage =
        OutputSongPage(songs, nextOffset?.let(::OutputKey))

    private data class OutputSongPage(
        override val songs: List<Song>,
        override val nextKey: SongPage.Key?,
    ) : SongPage {

        data class OutputKey(override val offset: UInt) : ItunesMusicPagingStrategy.OffsetKey
    }
}
