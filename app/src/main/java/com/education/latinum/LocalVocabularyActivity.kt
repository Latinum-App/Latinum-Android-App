package com.education.latinum

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class LocalVocabularyActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var vocabArrayList: ArrayList<Vocabulary>
    private lateinit var myAdapter: MyAdapter
    private lateinit var db: FirebaseFirestore
    lateinit var firebaseAuth: FirebaseAuth // Using firebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_vocabulary)

        firebaseAuth = FirebaseAuth.getInstance()   // Get the current firebaseAuth instance

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        vocabArrayList = arrayListOf()

        myAdapter = MyAdapter(vocabArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()

        var word = findViewById(R.id.word) as EditText // Get the data from the username input
        var word2 = word.getText()

        var translation = findViewById(R.id.translation) as EditText // Get the data from the username input
        var translation2 = translation.getText()

        findViewById<Button>(R.id.button10).setOnClickListener {
            val city = hashMapOf(
                "Word" to word2.toString(),
                "Translation" to translation2.toString(),
            )

            db.collection(firebaseAuth.currentUser?.uid.toString()).document()
                .set(city)
                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
        }

    }

    private fun EventChangeListener() {

        db = FirebaseFirestore.getInstance()
        var user = firebaseAuth.currentUser?.uid  // Display the users id
        db.collection(user.toString()).orderBy("Word",Query.Direction.ASCENDING).
        addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firebase Error",error.message.toString())
                    return
                }

                for (dc: DocumentChange in value?.documentChanges!!){

                    if (dc.type == DocumentChange.Type.ADDED){

                        vocabArrayList.add(dc.document.toObject(Vocabulary::class.java))

                    }
                }

                myAdapter.notifyDataSetChanged()
            }

        })

    }

}
