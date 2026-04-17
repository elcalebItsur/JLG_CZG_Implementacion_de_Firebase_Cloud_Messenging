package com.example.jgl_czg_firebase_cloud_messenging

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var textViewToken: TextView
    private lateinit var textViewLastMessage: TextView
    private lateinit var buttonCopyToken: Button

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notifications permission denied. You won't see notifications in foreground.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewToken = findViewById(R.id.textViewToken)
        textViewLastMessage = findViewById(R.id.textViewLastMessage)
        buttonCopyToken = findViewById(R.id.buttonCopyToken)

        buttonCopyToken.setOnClickListener {
            val token = textViewToken.text.toString()
            if (token != "Fetching token..." && token.isNotEmpty()) {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("FCM Token", token)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Token copied to clipboard", Toast.LENGTH_SHORT).show()
            }
        }

        askNotificationPermission()
        getFCMToken()
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // Permission already granted
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
                textViewToken.text = "Failed to get token"
                return@addOnCompleteListener
            }

            val token = task.result
            Log.d("MainActivity", "FCM Token: $token")
            textViewToken.text = token
        }
    }
}
