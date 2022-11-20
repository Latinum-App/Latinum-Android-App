package com.education.latinum

import java.util.*

data class VocabularyModel(
    var id: Int = getAutoId(),  // Every vocabulary is fetched by using a random id
    var word: String = "",  // Every word will be given out as a string
    var translation: String = ""    // Every translation will be given out as a string
){
    companion object {
        fun getAutoId(): Int {  // Function to get an id
            val random = Random()   // Just get a random value
            return  random.nextInt(100) // Get a random value every 100 milliseconds until you have them all
        }
    }
}