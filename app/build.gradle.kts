plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")

    id("com.google.gms.google-services")

}

android {
    namespace = "com.tayyipgunay.cryptotracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tayyipgunay.cryptotracker"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit - API istekleri için
    implementation(libs.retrofit)
    implementation(libs.converter.gson) // JSON dönüşümü için

    // Hilt - Dependency Injection
    implementation(libs.hilt.android)

    kapt(libs.hilt.compiler)


    // ✅ SADECE BUNU KULLAN!

    // Coroutine - Asenkron işlemler için
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // ViewModel & LiveData (Lifecycle Kütüphaneleri)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx.v262)

    // Jetpack Compose Destekleyici Kütüphaneler
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.runtime.livedata)
    // Coil - Görsel yükleme için
    implementation(libs.coil.compose)
    implementation(libs.androidx.hilt.navigation.compose) // ✅ Compose için Hilt desteği
    // 🔥 Firebase BOM (sadece bu yeterli)
    //implementation(platform("com.google.firebase:firebase-bom:32.1.1"))



    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))

}