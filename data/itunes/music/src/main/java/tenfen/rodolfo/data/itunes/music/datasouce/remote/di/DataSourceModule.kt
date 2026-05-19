package tenfen.rodolfo.data.itunes.music.datasouce.remote.di

import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tenfen.rodolfo.data.itunes.music.datasouce.remote.MusicItunesDataSource
import tenfen.rodolfo.data.itunes.music.datasouce.remote.factory.MusicFactory
import tenfen.rodolfo.data.itunes.music.datasouce.remote.factory.PageFactory
import tenfen.rodolfo.data.itunes.music.datasouce.remote.service.ItunesMusicPagingStrategy
import tenfen.rodolfo.data.itunes.music.datasouce.remote.service.ItunesMusicService
import tenfen.rodolfo.data.itunes.music.repository.port.MusicDataGateway
import tenfen.rodolfo.domain.music.entity.Album

internal val musicDataSourceModule = module {
    includes(itunesMusicServiceModule, musicFactoryModule)

    single<Json> {
        Json { ignoreUnknownKeys = true }
    }

    single<MusicDataGateway> {
        MusicItunesDataSource(
            get<ItunesMusicService>(),
            get<ItunesMusicPagingStrategy>(),
            get<MusicFactory>()::create,
            get<PageFactory>()::create,
            get<Json>(),
        )
    }

    single<Album.Id>(named("ALBUM_ID_DESERIALIZER")) { (stringRepresentation: String) ->
        MusicItunesDataSource.ItunesAlbumId(stringRepresentation.toLong())
    }
}
