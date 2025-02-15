// Download default models
import de.undercouch.gradle.tasks.download.Download
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("de.undercouch.download") version "5.4.0"
}

android {
    namespace = "ai.langindex"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}



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

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.jtokkit)
    implementation(libs.tasks.text)
    debugImplementation("io.objectbox:objectbox-android-objectbrowser:4.1.0")
    releaseImplementation("io.objectbox:objectbox-android:4.1.0")
}
apply(plugin = "io.objectbox")