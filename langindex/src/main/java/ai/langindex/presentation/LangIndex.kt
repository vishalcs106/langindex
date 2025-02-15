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

    fun processMessage(message: String): ProcessMessageResult {
        return repository.processMessage(message)
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
