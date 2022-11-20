package com.education.latinum

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth // Using firebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        firebaseAuth = FirebaseAuth.getInstance()   // Get the current firebaseAuth instance

        findViewById<TextView>(R.id.textView).text = firebaseAuth.currentUser?.uid  // Display the users id

        findViewById<TextView>(R.id.textView20).text = Global.klasse  // Display the users class

        val user = firebaseAuth.currentUser?.uid.toString()

        val docRef2 = db.collection("User").document(user)
        docRef2.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val teacher = snapshot.getString("teacher").toString()
                findViewById<TextView>(R.id.textView18).text = teacher // Display the users id
                Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")

            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }

        val docRef = db.collection("User").document(user)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val username = snapshot.getString("name")
                Log.d(TAG, "Current data: ${snapshot.data}")

                findViewById<TextView>(R.id.textView16).text = username

            } else {
                Log.d(TAG, "Current data: null")
            }
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            signOut()   // let the user sign out function
        }

        findViewById<Button>(R.id.button8).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/k7VytSrebt")))   // Send the user to our discord
        }

        findViewById<Button>(R.id.button19).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/LatinumApp/Latinum.App")))   // Send the user to our discord
        }

        var newusername = findViewById(R.id.username) as EditText // Get the data from the username input
        var username = newusername.getText()

        findViewById<Button>(R.id.button9).setOnClickListener {
            db.collection("User").document(user).update("name", username.toString())
        }

        var newclass = findViewById(R.id.klasse) as EditText // Get the data from the username input
        var newklasse = newclass.getText()

        findViewById<Button>(R.id.button13).setOnClickListener {
            db.collection("User").document(user).update("class", newklasse.toString())
        }

    }

    private fun signOut() { // Sign out function
        firebaseAuth.signOut()  // FirebaseAuth function to sign out
        startActivity(Intent(this, MainActivity::class.java))   // Send the user back to the main activity
        finish()    // Finish to disable the interaction to go back to this activity
    }
}