package tenfen.rodolfo.moisesaichallenge.presentation.album.details.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tenfen.rodolfo.domain.music.entity.Album
import tenfen.rodolfo.domain.music.usecase.di.useCaseMusicModule
import tenfen.rodolfo.moisesaichallenge.presentation.album.details.viewmodel.AlbumDetailsViewModel

val presentationAlbumDetailsModule = module {
    includes(useCaseMusicModule)

    viewModel { (serializedAlbumId: String) ->
        AlbumDetailsViewModel(
            albumId = get(named("ALBUM_ID_DESERIALIZER")) { parametersOf(serializedAlbumId) },
            getAlbumSongsUseCase = get(),
        )
    }
}
