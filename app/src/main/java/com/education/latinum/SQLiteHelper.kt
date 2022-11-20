package com.education.latinum

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import java.lang.Exception

    // Useless for now

/*class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "localVocabulary.db"
        private const val TBL_VOCABULARY = "tbl_vocabulary"
        private const val ID = "id"
        private const val WORD = "word"
        private const val TRANSLATION = "translation"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblVocabulary = ("CREATE TABLE " + TBL_VOCABULARY + "(" + ID + " INTEGER PRIMARY KEY," + WORD + " TEXT," + TRANSLATION + " TEXT" + ")")
        db?.execSQL(createTblVocabulary)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_VOCABULARY")
        onCreate(db)
    }

    fun insertVocabulary(std: VocabularyModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(WORD, std.word)
        contentValues.put(TRANSLATION, std.translation)

        val success = db.insert(TBL_VOCABULARY, null, contentValues)
        db.close()
        return success
    }

    fun getAllVocabulary(): ArrayList<VocabularyModel> {
        val stdList: ArrayList<VocabularyModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_VOCABULARY"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var word: String
        var translation: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                word = cursor.getString(cursor.getColumnIndex("name"))
                translation = cursor.getString(cursor.getColumnIndex("translation"))

                val std = VocabularyModel(id = id, word = word, translation = translation)
                stdList.add(std)
            } while (cursor.moveToNext())
        }

        return stdList

    }
}*/
