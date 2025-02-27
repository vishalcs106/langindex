package ai.langindex.data.repository

import ai.langindex.core.similarity.SimilarityCalculator
import ai.langindex.core.tokenizer.Tokenizer
import ai.langindex.data.local.EmbeddingEntity
import ai.langindex.data.local.EmbeddingEntity_

import ai.langindex.data.local.LangIndexStore
import ai.langindex.domain.model.ProcessMessageResult
import ai.langindex.domain.repository.ILangIndexRepository
import android.content.Context
import com.google.mediapipe.tasks.text.textembedder.TextEmbedder
import com.google.mediapipe.tasks.text.textembedder.TextEmbedderResult
import java.util.Locale


class LangIndexRepositoryImpl(
    private val tokenizer: Tokenizer,
    private val context: Context,
    private val modelType: ModelType = ModelType.AVERAGE_WORD
) : ILangIndexRepository {

    private val textEmbedder: TextEmbedder by lazy {
        val modelFileName = when (modelType) {
            ModelType.AVERAGE_WORD -> "average_word.tflite"
            ModelType.MOBILE_BERT -> "mobile_bert.tflite"
        }
        TextEmbedder.createFromFile(context, modelFileName)
    }

    override fun ingestMessage(message: String) {
        val input = message.lowercase(Locale.US)
        val embeddingResult: TextEmbedderResult = textEmbedder.embed(input)
        val embeddingVector = embeddingResult.embeddingResult().embeddings()
            .firstOrNull()
            ?.floatEmbedding()
            ?: throw IllegalStateException("Failed to generate embeddings")


        val box = LangIndexStore.getEmbeddingBox()

        val existing = box.query()
            .equal(EmbeddingEntity_.embedding, embeddingVector.toByteArray())
            .build()
            .findFirst()

        if (existing == null) {
            box.put(
                EmbeddingEntity(
                    textChunk = input,
                    embedding = embeddingVector.toByteArray()
                )
            )
        }
    }

    private fun ByteArray.toFloatArray(): FloatArray {
        val buffer = this.toByteBuffer()
        val floatArray = FloatArray(this.size / 4)
        buffer.asFloatBuffer().get(floatArray)
        return floatArray
    }

    private fun FloatArray.toByteArray(): ByteArray {
        val buffer = ByteArray(this.size * 4)
        this.forEachIndexed { index, value ->
            val bits = java.lang.Float.floatToIntBits(value)
            buffer[index * 4] = (bits shr 24).toByte()
            buffer[index * 4 + 1] = (bits shr 16).toByte()
            buffer[index * 4 + 2] = (bits shr 8).toByte()
            buffer[index * 4 + 3] = bits.toByte()
        }
        return buffer
    }

    private fun ByteArray.toByteBuffer() = java.nio.ByteBuffer.wrap(this)

    override fun retrieveChunks(message: String, storeMessage: Boolean): ProcessMessageResult {
        val input = message.lowercase(Locale.ROOT)
        val tokens = tokenizer.tokenize(input)
        val embeddingResult: TextEmbedderResult = textEmbedder.embed(input)
        val embeddingVector = embeddingResult.embeddingResult().embeddings()
            .firstOrNull()
            ?.floatEmbedding()
            ?: throw IllegalStateException("Failed to generate embeddings")

        val box = LangIndexStore.getEmbeddingBox()

        val allEmbeddings = LangIndexStore.getEmbeddingBox().all


        val similarChunks = allEmbeddings.map { entity ->
            val storedEmbedding = entity.embedding.toFloatArray()
            val similarity = SimilarityCalculator.cosineSimilarity(embeddingVector, storedEmbedding)

            Pair(entity.textChunk, similarity)
        }
            .sortedByDescending { it.second }
            .take(3)

        val retrievedChunks = similarChunks.map { it.first }

        val existing = box.query()
            .equal(EmbeddingEntity_.embedding, embeddingVector.toByteArray())
            .build()
            .findFirst()

        if (existing == null && storeMessage) {
            box.put(
                EmbeddingEntity(
                    textChunk = input,
                    embedding = embeddingVector.toByteArray()
                )
            )
        }

        return ProcessMessageResult(
            tokens = tokens,
            embedding = embeddingVector,
            retrievedChunks = retrievedChunks
        )
    }
}

enum class ModelType {
    AVERAGE_WORD,
    MOBILE_BERT
}