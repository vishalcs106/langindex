🔍 LangIndex – On-Device RAG SDK for Android

Fast, Lightweight, Privacy-Preserving Context Retrieval for LLMs

LangIndex is an on-device RAG (Retrieval-Augmented Generation) SDK for Android.It allows you to store and retrieve embeddings locally without relying on cloud-based vector DBs.

Built for privacy-first AI apps and offline AI agents.Optimized for integrating with both on-device and remote LLMs.

⭐ Key Features

👉 On-Device Storage – Stores embeddings using ObjectBox.👉 Semantic Search – Cosine similarity search over embeddings.👉 MediaPipe Embeddings – Uses TextEmbedder (MobileBERT / Average Word) models.👉 Tokenizer Included – JTokkit-based tokenization for LLM compatibility.👉 Offline RAG – Helps build LLM-powered apps with local context.👉 Open-Source & Easy to Integrate.

📦 Installation

Add JitPack to your project’s settings.gradle.kts:

dependencyResolutionManagement {
repositories {
google()
mavenCentral()
maven("https://jitpack.io")
}
}

Add LangIndex dependency to your app/build.gradle.kts:

dependencies {
implementation("com.github.vishalcs106:langindex:1.0.2")
}

🚀 Usage

1. Build LangIndex Instance

val langIndex = LangIndex.Builder()
.setModel(ModelType.AVERAGE_WORD) // or ModelType.MOBILE_BERT
.build(context)

2. Process a Message

val result = langIndex.processMessage("Tell me about Deadpool")

3. Access Results

val tokens: List<String> = result.tokens
val embedding: FloatArray = result.embedding
val retrievedChunks: List<String> = result.retrievedChunks

💠 Example

val langIndex = LangIndex.Builder().build(context)

val result = langIndex.processMessage("Tell me about Deadpool")

Log.d("Tokens", result.tokens.toString())
Log.d("Embedding", result.embedding.joinToString())
Log.d("Retrieved Chunks", result.retrievedChunks.toString())

💨 Real-World Use Case

Integrate LangIndex with Google Gemini, GPT, or LLaMA:

Retrieve relevant context from on-device storage.

Send retrieved context + user query to an LLM for better responses.

Maintain user privacy – No need to upload personal data for RAG!

📂 Models Used

LangIndex uses MediaPipe TextEmbedder:

Model

Size

MOBILE_BERT

20 MB

More accurate, better semantic understanding.

Models are auto-downloaded on first build and stored in assets/.

📚 How It Works

Tokenizes the message using JTokkit.

Generates embeddings using MediaPipe TextEmbedder.

Stores embeddings locally using ObjectBox.

Performs similarity search (cosine similarity) on stored embeddings.

Returns the top `` relevant text chunks along with tokens and the new embedding.

👨‍💻 Contributing

Feel free to submit issues, feature requests, or pull requests.

💟 License

Apache 2.0

🚀 Author

👋 Vishal Madhava – LinkedIn | Twitter

LangIndex is a first-principles RAG solution built with a vision for privacy-first AI on Android.LFG! 🚀

