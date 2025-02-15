package ai.langindex.domain.model

data class ProcessMessageResult(
    val tokens: List<String>,
    val embedding: FloatArray,
    val retrievedChunks: List<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProcessMessageResult

        if (tokens != other.tokens) return false
        if (!embedding.contentEquals(other.embedding)) return false
        if (retrievedChunks != other.retrievedChunks) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tokens.hashCode()
        result = 31 * result + embedding.contentHashCode()
        result = 31 * result + retrievedChunks.hashCode()
        return result
    }
}