package com.education.latinum

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

lateinit var firebaseAuth: FirebaseAuth // Using firebaseAuth
val db = Firebase.firestore

var word: String? = ""  // Every word will be given out as a string
var translation: String? = ""    // Every translation will be given out as a string

class OnlineVocabularyGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_vocabulary_game)

        firebaseAuth = FirebaseAuth.getInstance()   // Get the current firebaseAuth instance

        getVocabulary()
    }

fun getVocabulary() {
    val docRef = db.collection(Global.klasse).document()
    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                word = document.getString("Word")
                translation = document.getString("Translation")
            } else {
                Log.d(TAG, "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }
    }
}