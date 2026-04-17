package com.example.jgl_czg_firebase_cloud_messenging

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private lateinit var textViewLastMessageContent: TextView
    private lateinit var buttonCopyToken: Button

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, getString(R.string.identity), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permiso de notificaciones denegado.", Toast.LENGTH_LONG).show()
        }
    }

    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra("message")
            textViewLastMessage.text = getString(R.string.new_message_alert)
            textViewLastMessageContent.text = message
            textViewLastMessageContent.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewToken = findViewById(R.id.textViewToken)
        textViewLastMessage = findViewById(R.id.textViewLastMessage)
        textViewLastMessageContent = findViewById(R.id.textViewLastMessageContent)
        buttonCopyToken = findViewById(R.id.buttonCopyToken)

        buttonCopyToken.setOnClickListener {
            val token = textViewToken.text.toString()
            if (token != getString(R.string.fetching_token) && token.isNotEmpty()) {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("FCM Token", token)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, getString(R.string.token_copied), Toast.LENGTH_SHORT).show()
            }
        }

        askNotificationPermission()
        getFCMToken()
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(messageReceiver, IntentFilter("com.example.jgl_czg_fcm.UPDATE_UI"), RECEIVER_EXPORTED)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(messageReceiver)
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                textViewToken.text = "Error al obtener token"
                return@addOnCompleteListener
            }

            val token = task.result
            Log.d("MainActivity", "FCM Token: $token")
            textViewToken.text = token
        }
    }
}
