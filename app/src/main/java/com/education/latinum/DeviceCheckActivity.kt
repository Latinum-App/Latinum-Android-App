package com.education.latinum

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

    // This activity checks the current network state of the given device
    // ! At the moment you are able to bypass this check by using a fake internet connection

class DeviceCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_check)

        fun isNetwork(): Boolean {  // Bool to get a true or false answer
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return if(networkInfo != null && networkInfo.isConnected){
                true    // If there is a network connection -> true
            } else {
                return false    // If there is no network connection -> false or null
            }
        }

        if(isNetwork()){    // If the return was true

            val mainIntent = Intent(this, MainActivity::class.java) // Start a new main activity
            startActivity(mainIntent)
            finish()    // Finish to disable the interaction to go back to this activity
        }
        else{   // If the return was false or null

            val offlineIntent = Intent(this, OfflineMainActivity::class.java)   // Start a new offline main activity
            startActivity(offlineIntent)
            finish()    // Finish to disable the interaction to go back to this activity
        }
    }
}