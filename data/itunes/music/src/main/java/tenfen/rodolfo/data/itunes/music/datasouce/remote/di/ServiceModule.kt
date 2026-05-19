package tenfen.rodolfo.data.itunes.music.datasouce.remote.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import tenfen.rodolfo.data.itunes.music.datasouce.remote.service.ItunesMusicPagingStrategy
import tenfen.rodolfo.data.itunes.music.datasouce.remote.service.ItunesMusicService
import tenfen.rodolfo.data.itunes.music.datasouce.remote.service.ItunesMusicService.Companion.BASE_URL

const val ITUNES_API_PAGE_SIZE_DEPENDENCY_NAME = "ITUNES_API_PAGE_SIZE"

internal val itunesMusicServiceModule = module {
    single<MediaType> {
        "application/json; charset=UTF8".toMediaType()
    }

    single<Json> {
        Json {
            ignoreUnknownKeys = true
        }
    }

    factory<Converter.Factory> {
        get<Json>().asConverterFactory(get<MediaType>())
    }

    factory<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(get<Converter.Factory>())
            .build()
    }

    single<ItunesMusicService> {
        get<Retrofit>().create(ItunesMusicService::class.java)
    }

    single<ItunesMusicPagingStrategy> {
        ItunesMusicPagingStrategy.Discarding(
            pageSize = get(named(ITUNES_API_PAGE_SIZE_DEPENDENCY_NAME)),
        )
    }
}
