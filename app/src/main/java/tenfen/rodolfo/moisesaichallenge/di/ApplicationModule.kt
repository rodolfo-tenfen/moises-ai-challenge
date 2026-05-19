package tenfen.rodolfo.moisesaichallenge.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import tenfen.rodolfo.data.itunes.music.datasouce.remote.di.ITUNES_API_PAGE_SIZE_DEPENDENCY_NAME
import tenfen.rodolfo.data.itunes.music.repository.di.dataMusicModule
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.di.presentationAlbumDetailsModule
import tenfen.rodolfo.moisesaichallenge.presentation.songs.di.SONGS_SCREEN_PAGE_SIZE_DEPENDENCY_NAME
import tenfen.rodolfo.moisesaichallenge.presentation.songs.di.presentationSongsModule
import tenfen.rodolfo.moisesaichallenge.presentation.splash.di.presentationSplashModule

internal val applicationModule = module {
    includes(
        dataMusicModule,
        presentationSplashModule,
        presentationSongsModule,
        presentationAlbumDetailsModule,
    )

    single(named(ITUNES_API_PAGE_SIZE_DEPENDENCY_NAME)) {
        25u
    }

    single(named(SONGS_SCREEN_PAGE_SIZE_DEPENDENCY_NAME)) {
        get<UInt>(named(ITUNES_API_PAGE_SIZE_DEPENDENCY_NAME))
    }
}
