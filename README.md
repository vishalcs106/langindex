# LangIndex – On-Device RAG SDK for Android

**Fast, Lightweight, Privacy-Preserving Context Retrieval for LLMs**

LangIndex is an on-device RAG (Retrieval-Augmented Generation) SDK for Android. It allows you to store and retrieve embeddings locally without relying on cloud-based vector databases.

Built for privacy-first AI apps and offline AI agents. Optimized for integrating with both on-device and remote LLMs.

---

[![](https://jitpack.io/v/vishalcs106/langindex.svg)](https://jitpack.io/#vishalcs106/langindex)

## ⭐ Key Features

- **On-Device Storage** – Stores embeddings using ObjectBox.
- **Semantic Search** – Cosine similarity search over embeddings.
- **MediaPipe Embeddings** – Uses TextEmbedder (MobileBERT / Average Word) models.
- **Tokenizer Included** – JTokkit-based tokenization for LLM compatibility.
- **Offline RAG** – Helps build LLM-powered apps with local context.
- **Open-Source & Easy to Integrate.**

---

## 📦 Installation

Add JitPack to your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

Add LangIndex dependency to your `app/build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.vishalcs106:langindex:1.0.2")
}
```

## 🚀 Usage

### 1. Build LangIndex Instance

```kotlin
val langIndex = LangIndex.Builder()
    .build(context)
```

### 2. Process a Message

```kotlin
val result = langIndex.retrieveChunks("Tell me about Deadpool", storeQuery = false)
```

### 3. Access Results

```kotlin
val tokens: List<String> = result.tokens
val embedding: FloatArray = result.embedding
val retrievedChunks: List<String> = result.retrievedChunks
```

## 💠 Example

```kotlin
val langIndex = LangIndex.Builder().build(context)
val result = langIndex.retrieveChunks("Tell me about Deadpool")

Log.d("Tokens", result.tokens.toString())
Log.d("Embedding", result.embedding.joinToString())
Log.d("Retrieved Chunks", result.retrievedChunks.toString())
```
---

## 💨 Real-World Use Case

Integrate LangIndex with Google Gemini, GPT, or LLaMA:

- **Retrieve relevant context** from on-device storage.
- **Send retrieved context + user query** to an LLM for better responses.
- **Maintain user privacy** – No need to upload personal data for RAG!

---

## 📂 Models Used

LangIndex uses MediaPipe TextEmbedder:

| Model       | Size  |
|-------------|-------|
| MOBILE_BERT | 20 MB |

- More accurate with better semantic understanding.
- Models are auto-downloaded on first build and stored in `assets/`.

---

## 📚 How It Works

- **Tokenization:** Uses JTokkit for tokenizing the message.
- **Embedding Generation:** Generates embeddings using MediaPipe TextEmbedder.
- **Local Storage:** Stores embeddings locally using ObjectBox.
- **Similarity Search:** Performs cosine similarity search on stored embeddings.
- **Result:** Returns the top relevant text chunks along with tokens and the new embedding.

---

## 👨‍💻 Contributing

Feel free to submit issues, feature requests, or pull requests.

---

## 💟 License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

---

## 🚀 Author

**Vishal Madhava**  
[LinkedIn](https://www.linkedin.com/in/vishal106/) | [Twitter](https://x.com/vishal_avax)

---

LangIndex is a first-principles RAG solution built with a vision for privacy-first AI on Android. LFG! 🚀
