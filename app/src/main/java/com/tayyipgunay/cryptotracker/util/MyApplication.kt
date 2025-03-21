package com.tayyipgunay.cryptotracker.util

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){
override fun onCreate() {
    super.onCreate()

    // Firebase'ı elle başlat
    FirebaseApp.initializeApp(this)
}
}