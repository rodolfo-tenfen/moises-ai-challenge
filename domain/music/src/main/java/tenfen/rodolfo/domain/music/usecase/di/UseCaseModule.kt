package tenfen.rodolfo.domain.music.usecase.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tenfen.rodolfo.domain.music.usecase.GetAlbumSongsUseCase
import tenfen.rodolfo.domain.music.usecase.PagedSongSearchUseCase
import tenfen.rodolfo.domain.music.usecase.port.GetAlbumSongsInputPort
import tenfen.rodolfo.domain.music.usecase.port.PagedSongSearchInputPort

val useCaseMusicModule = module {
    singleOf(::PagedSongSearchUseCase) bind PagedSongSearchInputPort::class

    singleOf(::GetAlbumSongsUseCase) bind GetAlbumSongsInputPort::class
}
