package com.education.latinum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button

// This activity is made to say to the user that the device does not have a internet connection, so the app is not able to load

class OfflineMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_main)

        findViewById<Button>(R.id.button11).setOnClickListener {
            val devicecheckIntent = Intent(this, DeviceCheckActivity::class.java)
            startActivity(devicecheckIntent)
        }

    }
}