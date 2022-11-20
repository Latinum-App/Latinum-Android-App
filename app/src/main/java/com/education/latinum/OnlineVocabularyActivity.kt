package com.education.latinum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class OnlineVocabularyActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var vocabArrayList: ArrayList<Vocabulary>
    private lateinit var myAdapter: MyAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_vocabulary)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        vocabArrayList = arrayListOf()

        myAdapter = MyAdapter(vocabArrayList)

        recyclerView.adapter = myAdapter

        firebaseAuth = FirebaseAuth.getInstance()   // Get the current firebaseAuth instance

        EventChangeListener()

        findViewById<FloatingActionButton>(R.id.floatingActionButton4).setOnClickListener{
            startActivity(Intent(this, OnlineVocabularyGameActivity::class.java))
        }
    }

    private fun EventChangeListener() {

    if(Global.klasse == "null")
    {
        startActivity(Intent(this, JoinClassActivity::class.java))
        finish()
    }
    else {

        db = FirebaseFirestore.getInstance()
        db.collection(Global.klasse).orderBy("Word", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firebase Error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {

                        if (dc.type == DocumentChange.Type.ADDED) {

                            vocabArrayList.add(dc.document.toObject(Vocabulary::class.java))

                        }
                    }

                    myAdapter.notifyDataSetChanged()
                }
            })
    }
    }
}