package ai.langindex.core.tokenizer

import com.knuddels.jtokkit.Encodings
import com.knuddels.jtokkit.api.Encoding
import com.knuddels.jtokkit.api.EncodingRegistry
import com.knuddels.jtokkit.api.EncodingType



interface Tokenizer {
    fun tokenize(input: String): List<String>
    fun countTokens(input: String): Int
}

class JTokkitTokenizer : Tokenizer {

    private val registry: EncodingRegistry = Encodings.newDefaultEncodingRegistry()
    var encoding: Encoding = registry.getEncoding(EncodingType.CL100K_BASE)

    override fun tokenize(input: String): List<String> {
        val tokenIds = encoding.encode(input)
        return tokenIds.toArray().map { it.toString() }
    }


    override fun countTokens(input: String): Int {
        return encoding.countTokens(input)
    }
}