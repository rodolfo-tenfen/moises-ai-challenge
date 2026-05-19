package tenfen.rodolfo.data.itunes.music.datasouce.remote.dto

import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SongBody(
    @SerialName("trackId") val id: Long,
    @SerialName("trackName") val name: String,
    val artistName: String,
    @SerialName("artistViewUrl") val artistImageUrl: String,
    @SerialName("collectionId") val albumId: Long,
    @SerialName("collectionName") val albumName: String,
    @SerialName("artworkUrl100") val albumImageUrl: String,
    val releaseDate: Instant,
    @SerialName("trackExplicitness") val explicitness: String,
    @SerialName("trackTimeMillis") val durationMs: Long,
    @SerialName("primaryGenreName") val genreName: String,
)

@Serializable
internal data class SongResultBody(
    @SerialName("resultCount") val count: Int,
    val results: List<SongBody>,
)
