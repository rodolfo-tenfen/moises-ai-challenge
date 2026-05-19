package tenfen.rodolfo.data.itunes.music.datasouce.remote.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tenfen.rodolfo.data.itunes.music.datasouce.remote.factory.MusicFactory
import tenfen.rodolfo.data.itunes.music.datasouce.remote.factory.PageFactory

internal val musicFactoryModule = module {
    singleOf(::MusicFactory)

    singleOf(::PageFactory)
}
