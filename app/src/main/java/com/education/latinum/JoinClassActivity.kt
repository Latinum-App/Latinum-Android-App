package com.education.latinum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class JoinClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_class)

        val user = firebaseAuth.currentUser?.uid.toString()

        var newclass = findViewById(R.id.klasse2) as EditText // Get the data from the username input
        var newklasse = newclass.getText()

        findViewById<Button>(R.id.button14).setOnClickListener {
            db.collection("User").document(user).update("class", newklasse.toString())
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}