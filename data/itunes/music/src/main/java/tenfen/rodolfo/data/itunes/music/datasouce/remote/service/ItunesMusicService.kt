package tenfen.rodolfo.data.itunes.music.datasouce.remote.service

import retrofit2.http.GET
import retrofit2.http.Query
import tenfen.rodolfo.data.itunes.music.datasouce.remote.dto.AlbumSongsResultBody
import tenfen.rodolfo.data.itunes.music.datasouce.remote.dto.SongResultBody

internal interface ItunesMusicService {

    @GET("search?entity=song")
    suspend fun search(
        @Query("term") term: String,
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int,
    ): SongResultBody

    @GET("lookup?entity=song")
    suspend fun lookupSongsForAlbum(@Query("id") id: Long): AlbumSongsResultBody

    companion object {

        const val BASE_URL = "https://itunes.apple.com/"
    }
}
