package app.android.langindexdemo

import LangIndexDemoTheme
import ai.langindex.core.tokenizer.JTokkitTokenizer
import ai.langindex.domain.model.ProcessMessageResult
import ai.langindex.presentation.LangIndex
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LangIndexDemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val langIndex = remember { LangIndex.Builder().build(context)}

    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    var retrievedChunks by remember { mutableStateOf<ProcessMessageResult?>(null) }
    var tokenCount by remember { mutableStateOf(0) }
    var lastEmbedding by remember { mutableStateOf<FloatArray?>(null) }

    val testInputs = listOf(
        "Deadpool looks like wolverine",
        "Deadpool wears red suite",
        "Tell me about Marvel superheroes.",
        "What are embeddings in AI?",
        "Deadpool is a Marvel character known for his humor.",
        "Explain what LangChain is in simple terms.",
        "Vector databases store embeddings for similarity search.",
        "Cosine similarity measures the angle between vectors.",
        "OpenAI created GPT-4 for natural language processing.",
        "The quick brown fox jumps over the lazy dog."
    )

    testInputs.forEach { input ->
        langIndex.ingestMessage(input)
    }

    val result = langIndex.retrieveChunks("tell me about the Marvel character Deadpool.", false)
    Log.d("result", result.retrievedChunks.toString())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text Input Field
        BasicTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp) // Adjust height as needed
                .padding(8.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
            maxLines = Int.MAX_VALUE // Allows multi-line input
        )


        // Process Button
        Button(
            onClick = {
                if (inputText.text.isNotEmpty()) {
                    // Process the message
                    retrievedChunks = langIndex.retrieveChunks(inputText.text, false)
                    Log.d("result2", retrievedChunks?.retrievedChunks.toString())
                }
            }
        ) {
            Text("Process")
        }

        // Display Results
        Text("Tokens:${retrievedChunks?.tokens?.size ?: "0"}")
        Text("Retrieved Chunks:")
        retrievedChunks?.retrievedChunks?.forEach { chunk ->
            Text("- $chunk")
       }

        lastEmbedding?.let { embedding ->
            Text("Embedding (first 5 values): ${embedding.take(5).joinToString()}")
        }
    }
}
