buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("io.objectbox:objectbox-gradle-plugin:4.1.0")
    }
}

plugins {
    id("com.android.application") version "8.7.2" apply false
    id("com.android.library") version "8.7.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    // ✅ Optional: Can apply false, but not needed globally
    // id("de.undercouch.download") version "5.4.0" apply false
}
