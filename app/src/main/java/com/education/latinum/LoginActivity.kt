package com.education.latinum

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoginActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 120
    }

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<Button>(R.id.button).setOnClickListener {
            anonymousAuth()
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            googleAuth()
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            loginAuth()
        }

        findViewById<Button>(R.id.button5).setOnClickListener {
            registerAuth()
        }
    }

    private fun registerAuth() {    // The function the register a account with email

        val email = findViewById<TextInputEditText>(R.id.email) // Get the data from the email input
        val password = findViewById<TextInputEditText>(R.id.password)   // Get the data from the password input

        firebaseAuth.createUserWithEmailAndPassword(    // Create a account using firebaseAuth with the given email and password
            email.text.toString().trim(),
            password.text.toString().trim()

        )
            .addOnSuccessListener { // If the account is successfully registered
                startActivity(Intent(this, DeviceCheckActivity::class.java))   // Go back to your main activity
                Log.d("Success", "Success")

                createUsername()
                createHighscore()
                createLocalVocabulary()

                finish()    // Finish to disable the interaction to go back to this activity
        }
            .addOnFailureListener{  // If there is an error
                Log.d("Error", "Error")
            }
    }

    private fun loginAuth() {   // The function the login a account with email

        val email = findViewById<TextInputEditText>(R.id.email) // Get the data from the email input
        val password = findViewById<TextInputEditText>(R.id.password)   // Get the data from the password input

        firebaseAuth.signInWithEmailAndPassword(    // Log into a account using firebaseAuth with the given email and password
            email.text.toString().trim(),
            password.text.toString().trim()
        )
            .addOnSuccessListener { // If the user is successfully logged in
                startActivity(Intent(this, DeviceCheckActivity::class.java))   // Go back to your main activity
                Log.d("Success", "Success")

                finish()    // Finish to disable the interaction to go back to this activity
            }
            .addOnFailureListener{  // If there is an error
                Log.d("Error", "Error")
            }
    }

    private fun anonymousAuth() {   // The function the login a account with email
        firebaseAuth.signInAnonymously()    // Firebase anonymous function
            .addOnSuccessListener { // If the account is successfully registered
                startActivity(Intent(this, DeviceCheckActivity::class.java))   // Go back to your main activity
                Log.d("Success", "Success")

                createUsername()
                createHighscore()
                createLocalVocabulary()

                finish()    // Finish to disable the interaction to go back to this activity
            }
            .addOnFailureListener{  // If there is an error
                Log.d("Error", "Error")
            }
    }

    private fun googleAuth() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("Success", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("Error", "Google sign in failed", e)
                }
            }else{
                Log.w("Error", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "signInWithCredential:success")
                    startActivity(Intent(this, DeviceCheckActivity::class.java))

                    val user = hashMapOf(
                        "name" to firebaseAuth.currentUser?.uid.toString(),
                    )

                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Error", "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun createUsername(){

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)

        val user = hashMapOf(
            "name" to "null", // Set the value name to null
            "teacher" to "false",
            "class" to "null",
            "joindate" to "$formatted",
        )

        db.collection("User")
            .document(firebaseAuth.currentUser?.uid.toString())    // Create a document in our firebase database with the new user id as the name
            .set(user)  // Write the user value (null) into the new document
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener {createUsername() }
    }

    private fun createHighscore(){
        val score = hashMapOf(
            "score" to "0", // Set the value name to the username
        )

        db.collection("Highscore")
            .document(firebaseAuth.currentUser?.uid.toString())    // Create a document in our firebase database with the new user id as the name
            .set(score)  // Write the user value into the new document
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { createHighscore() }
    }

    private fun createLocalVocabulary(){
        val localvocabulary = hashMapOf(
            "Word" to "My first word",
            "Translation" to "My first Translation",
        )

        db.collection(firebaseAuth.currentUser?.uid.toString()).document()
            .set(localvocabulary)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { createLocalVocabulary() }
    }
}