package ai.langindex.data.local

import android.content.Context
import android.util.Log
import com.google.android.datatransport.BuildConfig
import io.objectbox.BoxStore
import io.objectbox.android.Admin


object LangIndexStore {
    lateinit var boxStore: BoxStore

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
        if (!BuildConfig.DEBUG) {
            val started = Admin(boxStore).start(context)
            Log.i("ObjectBoxAdmin", "Started: $started")
        }
    }

    fun getEmbeddingBox() = boxStore.boxFor(EmbeddingEntity::class.java)
}