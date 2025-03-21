// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.0" apply false  // Kotlin Kapt plugin
    id("com.google.gms.google-services") version "4.4.2" apply false


}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
        classpath ("com.google.gms:google-services:4.4.2")

      /*  classpath ("com.google.gms:google-services:4.3.10") // Firebase Google Services eklentisi
        classpath("com.google.firebase:firebase-messaging:24.1.0")
        classpath("com.google.firebase:firebase-messaging-ktx:24.1.0")
        classpath ("com.google.gms:google-services:4.3.10")
*/




    }
}
