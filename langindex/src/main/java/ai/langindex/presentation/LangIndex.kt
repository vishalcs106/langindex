package ai.langindex.presentation

import ai.langindex.core.tokenizer.JTokkitTokenizer
import ai.langindex.data.local.LangIndexStore
import ai.langindex.data.repository.LangIndexRepositoryImpl
import ai.langindex.domain.model.ProcessMessageResult
import ai.langindex.domain.repository.ILangIndexRepository
import android.content.Context


class LangIndex private constructor(
    private val repository: ILangIndexRepository
) {

    /**
     * Stores a new message embedding in the local database.
     */
    fun ingestMessage(message: String) {
        repository.ingestMessage(message)
    }

    /**
     * Retrieves relevant stored chunks for a given query.
     * If storeQuery is true, the query embedding will also be stored.
     */
    fun retrieveChunks(query: String, storeQuery: Boolean): ProcessMessageResult {
        return repository.retrieveChunks(query, storeQuery)
    }

    class Builder {
        fun build(context: Context): LangIndex {
            val tokenizer = JTokkitTokenizer()
            val repository = LangIndexRepositoryImpl(tokenizer, context)
            init(context)
            return LangIndex(repository)
        }
    }
    companion object {
        fun init(context: Context) {
            if (!initialized) {
                LangIndexStore.init(context)
                initialized = true
            }
        }

        private var initialized: Boolean = false
    }
}
