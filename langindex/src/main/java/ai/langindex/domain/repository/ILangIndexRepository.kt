package ai.langindex.domain.repository

import ai.langindex.domain.model.ProcessMessageResult

interface ILangIndexRepository {
    fun processMessage(message: String): ProcessMessageResult
}