package com.education.latinum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

    // This activity is made to check if a account is logged in and to redirect to user to a new activity

class MainActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth // Using firebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()   // Get the current instance of firebaseAuth

        val user = firebaseAuth.currentUser?.uid    // User is now the id for a logged in account

        Handler().postDelayed({
            if(user != null) {  // If user is not null it means that a account must be logged in
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)
                finish()    // Finish to disable the interaction to go back to this activity
            }else{  // If user is null it's not possible for an account to be logged in
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()    // Finish to disable the interaction to go back to this activity
            }
        }, 2000)    // Show this activity for 2000 milliseconds
    }
}