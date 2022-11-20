package com.education.latinum

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity() : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    val db = Firebase.firestore
    var username: String = ""
    var teacher: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        firebaseAuth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.button6).setOnClickListener {
            val localVocIntent = Intent(this, LocalVocabularyActivity::class.java)
            startActivity(localVocIntent)
        }

        findViewById<Button>(R.id.button18).setOnClickListener {
            val userIntent = Intent(this, UserActivity::class.java)
            startActivity(userIntent)
        }

        findViewById<Button>(R.id.button15).setOnClickListener {
            val learningIntent = Intent(this, LearningActivity::class.java)
            startActivity(learningIntent)
        }

        findViewById<Button>(R.id.button7).setOnClickListener {
            val onlineVocIntent = Intent(this, OnlineVocabularyActivity::class.java)
            startActivity(onlineVocIntent)
        }

        val user = firebaseAuth.currentUser?.uid.toString()

        val docRef2 = db.collection("User").document(user)
        docRef2.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                teacher = snapshot.getString("teacher").toString()
                Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")

            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }

        val docRef3 = db.collection("User").document(user)
        docRef3.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Global.klasse = snapshot.getString("class").toString()
                Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")

            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }

        val docRef4 = db.collection("User").document(user)
        docRef4.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                findViewById<TextView>(R.id.textView25).text = snapshot.getString("joindate").toString()
                Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")

            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }

        val docRef = db.collection("User").document(user)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(ContentValues.TAG, "Current data: ${snapshot.data}")

                username = snapshot.getString("name").toString()

            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }

            if(username == "null")
                {
                    if(teacher == "true")
                    {
                        findViewById<TextView>(R.id.textView10).text = ("Salvete, magister \uD83D\uDC4B")
                    }
                    else if(teacher == "false"){
                        findViewById<TextView>(R.id.textView10).text = ("Salvete, discipulus \uD83D\uDCDA")
                    }
                }
            else
                {
                    if(teacher == "true")
                    {
                        findViewById<TextView>(R.id.textView10).text = ("Salvete, magister \uD83D\uDC4B")
                    }
                    else if(teacher == "false"){
                        findViewById<TextView>(R.id.textView10).text = ("Salvete, discipulus \uD83D\uDCDA")
                    }
                }

            findViewById<Button>(R.id.button16).setOnClickListener {
                if(teacher == "true")
                {
                    val teacherIntent = Intent(this, TeacherActivity::class.java)
                    startActivity(teacherIntent)
                }
                else if(teacher == "false"){
                    val errorIntent = Intent(this, ErrorTeacherActivity::class.java)
                    startActivity(errorIntent)
                }
            }

            findViewById<Button>(R.id.button17).setOnClickListener {
                if(Global.klasse == "null")
                {
                    val errorIntent = Intent(this, JoinClassActivity::class.java)
                    startActivity(errorIntent)
                }
                else {
                    val classIntent = Intent(this, ClassActivity::class.java)
                    startActivity(classIntent)
                }
            }
        }
    }
}