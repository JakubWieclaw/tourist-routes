package com.example.hikingtrails

import Trail
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory

// Class to handle creation and connection to the database
class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object {
                private const val DATABASE_VERSION = 1
                private const val DATABASE_NAME = "DB"
                private const val TABLE_NAME = "Trails"
                private const val KEY_ID = "id"
                private const val KEY_NAME = "name"
                private const val KEY_DESCRIPTION = "description"
                private const val KEY_IMAGE = "image"
        }

        // Function to create the database
        override fun onCreate(db: SQLiteDatabase?) {
                val CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                        + KEY_DESCRIPTION + " TEXT," + KEY_IMAGE + " BLOB" + ")")
                db?.execSQL(CREATE_TABLE)
        }

        // Function to upgrade the database
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
                onCreate(db)
        }

        // Function to insert data into the database
        fun insertData(name: String, description: String, image: ByteArray?) {
                val db = this.writableDatabase
                val values = ContentValues()
                values.put(KEY_NAME, name)
                values.put(KEY_DESCRIPTION, description)
                values.put(KEY_IMAGE, image)
                db.insert(TABLE_NAME, null, values)
                db.close()
        }

        // Function to read data from the database
        // Function to read data from the database
        fun readData() : List<Trail> {
                val list = ArrayList<Trail>()
                val selectQuery = "SELECT  * FROM $TABLE_NAME"
                val db = this.readableDatabase
                val cursor = db.rawQuery(selectQuery, null)
                if (cursor.moveToFirst()) {
                        do {
                                val id = cursor.getInt(0)
                                val name = cursor.getString(1)
                                val description = cursor.getString(2)
                                val imageBlob = cursor.getBlob(3)
                                val image = if (imageBlob != null) BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.size) else null
                                list.add(Trail(id, name, description, image))
                        } while (cursor.moveToNext())
                }
                cursor.close()
                db.close()
                return list
        }

        // Insert example data into the database
        fun insertExampleData() {
                insertData("Szlak 1", "Opis szlaku 1", null)
                insertData("Szlak 2", "Opis szlaku 2", null)
                insertData("Szlak 3", "Opis szlaku 3", null)
        }
}
