package ai.langindex.data.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class EmbeddingEntity(
    @Id var id: Long = 0,
    var textChunk: String,
    var embedding: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmbeddingEntity

        if (id != other.id) return false
        if (textChunk != other.textChunk) return false
        if (!embedding.contentEquals(other.embedding)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + textChunk.hashCode()
        result = 31 * result + embedding.contentHashCode()
        return result
    }
}