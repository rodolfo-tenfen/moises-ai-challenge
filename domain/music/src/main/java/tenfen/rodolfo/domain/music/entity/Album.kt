package tenfen.rodolfo.domain.music.entity

interface Album {
    val id: Id

    val name: String

    val imageUrl: String

    interface Id
}
