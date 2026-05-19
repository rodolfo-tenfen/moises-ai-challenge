package tenfen.rodolfo.data.itunes.music.repository.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tenfen.rodolfo.data.itunes.music.datasouce.remote.di.musicDataSourceModule
import tenfen.rodolfo.data.itunes.music.repository.MusicRepository
import tenfen.rodolfo.domain.music.usecase.port.MusicGateway

val dataMusicModule = module {
    includes(musicDataSourceModule)

    singleOf(::MusicRepository) bind MusicGateway::class
}
