package com.tayyipgunay.cryptotracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.MainThread
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.internal.composableLambdaNInstance

import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.tayyipgunay.cryptotracker.presentation.cryptodetails.views.CryptoDetailScreen
import com.tayyipgunay.cryptotracker.ui.theme.CryptoTrackerTheme

import com.tayyipgunay.cryptotracker.presentation.cryptolist.views.CryptoListScreen
import com.tayyipgunay.cryptotracker.presentation.cryptolist.views.CryptoTopBar
import com.tayyipgunay.cryptotracker.service.FirebaseNotificationService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.Permission
import java.util.jar.Manifest
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
        createNotificationChannel()

        try {
            FirebaseApp.initializeApp(applicationContext)
//    FirebaseMessaging.getInstance().setAutoInitEnabled(true);


            getFirebaseToken()
        } catch (e: Exception) {
            println(e.message)
        }
        setContent {

            CryptoTrackerTheme {
                val navController = rememberNavController()



                NavHost(navController = navController, startDestination = "crypto_list_screen") {
                    composable(route = "crypto_list_screen") {
                        CryptoListScreen(navController = navController)
                    }
                    composable(route = "crypto_details_screen/{cryptoId}") { newBackStackEntry ->
                        val cryptoId = newBackStackEntry.arguments?.getString("cryptoId")
                        println(cryptoId + "  crypto id geldi..")

                        CryptoDetailScreen(cryptoId!!)
                    }
                }

            }
        }
    }

    fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FirebaseToken", "Token alınamadı", task.exception)
                    println("token alınmamd")
                    return@addOnCompleteListener
                }

                val token = task.result
                Log.d("FirebaseToken", "Firebase Token: $token")
            }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "crypto_alerts", // Kanal ID
                "Crypto Alerts", // Kanal İsmi (kullanıcıya görünür)
                NotificationManager.IMPORTANCE_HIGH // Önem seviyesi
            )
            channel.description = "Kripto para fiyatlarıyla ilgili bildirimler"

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }




    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CryptoTrackerTheme {
            //  CryptoListScreen()

            //    CryptoDetailScreen()


        }
    }
}

