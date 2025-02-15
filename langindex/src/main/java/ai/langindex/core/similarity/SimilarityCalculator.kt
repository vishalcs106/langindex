package ai.langindex.core.similarity

import kotlin.math.sqrt

object SimilarityCalculator {

    /**
     * Calculates cosine similarity between two embeddings (vectors).
     */
    fun cosineSimilarity(a: FloatArray, b: FloatArray): Float {
        if (a.size != b.size) {
            throw IllegalArgumentException("Vectors must be of the same size")
        }

        var dotProduct = 0f
        var normA = 0f
        var normB = 0f

        for (i in a.indices) {
            dotProduct += a[i] * b[i]
            normA += a[i] * a[i]
            normB += b[i] * b[i]
        }

        val denominator = sqrt(normA) * sqrt(normB)
        if (denominator == 0f) {
            return 0f // Avoid division by zero (e.g., zero vectors)
        }

        return dotProduct / denominator
    }
}