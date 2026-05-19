package tenfen.rodolfo.data.itunes.music.datasouce.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
internal data class AlbumSongsResultBody(
    @SerialName("resultCount") val count: Int,
    val results: List<JsonObject>,
)
