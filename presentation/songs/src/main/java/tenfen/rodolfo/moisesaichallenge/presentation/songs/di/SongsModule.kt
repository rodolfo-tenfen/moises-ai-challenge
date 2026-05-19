package tenfen.rodolfo.moisesaichallenge.presentation.songs.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tenfen.rodolfo.domain.music.usecase.di.useCaseMusicModule
import tenfen.rodolfo.moisesaichallenge.presentation.songs.viewmodel.SongsViewModel

const val SONGS_SCREEN_PAGE_SIZE_DEPENDENCY_NAME = "SONGS_SCREEN_PAGE_SIZE"

val presentationSongsModule = module {
    includes(useCaseMusicModule)

    viewModel {
        SongsViewModel(
            pagedSongSearchUseCase = get(),
            pageSize = get(named(SONGS_SCREEN_PAGE_SIZE_DEPENDENCY_NAME)),
        )
    }
}
