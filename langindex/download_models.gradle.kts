import de.undercouch.gradle.tasks.download.Download

val assetDir = "${projectDir}/src/main/assets"

tasks.register<Download>("downloadMobileBertModel") {
    src("https://storage.googleapis.com/mediapipe-models/text_embedder/bert_embedder/float32/1/bert_embedder.tflite")
    dest(file("$assetDir/mobile_bert.tflite"))
    overwrite(false)
}

tasks.register<Download>("downloadAverageWordModel") {
    src("https://storage.googleapis.com/mediapipe-models/text_embedder/average_word_embedder/float32/1/average_word_embedder.tflite")
    dest(file("$assetDir/average_word.tflite"))
    overwrite(false)
}

tasks.named("preBuild") {
    dependsOn("downloadMobileBertModel", "downloadAverageWordModel")
}
