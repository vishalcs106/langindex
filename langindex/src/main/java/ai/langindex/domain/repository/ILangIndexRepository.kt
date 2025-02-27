package ai.langindex.domain.repository

import ai.langindex.domain.model.ProcessMessageResult

interface ILangIndexRepository {
    fun ingestMessage(message: String)
    fun retrieveChunks(message: String, storeMessage: Boolean): ProcessMessageResult
}