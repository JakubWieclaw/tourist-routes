package com.example.hikingtrails

import Trail
import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

// Class to handle creation and connection to the database
class DatabaseHandler(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
                private const val DATABASE_VERSION = 2
                private const val DATABASE_NAME = "DB"
                private const val TABLE_NAME = "Trails"
                private const val KEY_ID = "id"
                private const val KEY_NAME = "name"
                private const val KEY_DESCRIPTION = "description"
                private const val KEY_IMAGE = "image"
                private const val TIME_MEASURMENT_TABLE_NAME = "TimeMeasurement"
        }

        // Function to create the database
        override fun onCreate(db: SQLiteDatabase?) {
                // if table is not created, create the table, otherwise do nothing
                createBaseTable(db)
                createTimeMeasurmentTable(db)
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
        fun readData(): List<Trail> {
                // if there is no data or Database, create the table
                val list = ArrayList<Trail>()
                val selectQuery = "SELECT  * FROM $TABLE_NAME"
                val db = this.readableDatabase
                val cursor = db.rawQuery(selectQuery, null)
                if (cursor.moveToFirst()) {
                        do {
                                val id = cursor.getInt(0)
                                val name = cursor.getString(1)
                                val description = cursor.getString(2)
                                val imageBytes = cursor.getBlob(3)
                                val image = if (imageBytes != null) BitmapFactory.decodeByteArray(
                                        imageBytes,
                                        0,
                                        imageBytes.size
                                ) else null
                                list.add(Trail(id, name, description, image))
                        } while (cursor.moveToNext())
                }
                cursor.close()
                db.close()
//        if (list.isEmpty()) {
//            insertExampleData(context)
//        }

                return list
        }

        fun insertExampleData(context: Context) {
                // if table is empty, insert example data
                if (readData().isEmpty()) {
                        val jpg1 = BitmapFactory.decodeResource(context.resources, R.drawable.photo_2)
                        val jpg2 = BitmapFactory.decodeResource(context.resources, R.drawable.photo_2)
                        val jpg3 = BitmapFactory.decodeResource(context.resources, R.drawable.photo_2)

                        insertData("Szlak 1", "Opis szlaku 1", getBitmapAsByteArray(jpg1))
                        insertData("Szlak 2", "Opis szlaku 2", getBitmapAsByteArray(jpg2))
                        insertData("Szlak 3", "Opis szlaku 3", getBitmapAsByteArray(jpg3))
                }
        }

        private fun getBitmapAsByteArray(bitmap: Bitmap?): ByteArray? {
                if (bitmap == null) return null
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
                return stream.toByteArray()
        }


        fun createBaseTable(db: SQLiteDatabase?) {
                val CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                        + KEY_DESCRIPTION + " TEXT," + KEY_IMAGE + " BLOB" + ")")
                db?.execSQL(CREATE_TABLE)

        }

        // Create table for time measurments with foregin key to trails
        fun createTimeMeasurmentTable(db: SQLiteDatabase?) {
                val CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TIME_MEASURMENT_TABLE_NAME + "("
                        + "id INTEGER PRIMARY KEY," + "time INTEGER," + "trail_id INTEGER," + "FOREIGN KEY(trail_id) REFERENCES Trails(id)" + ")")
                db?.execSQL(CREATE_TABLE)
        }

        // Delete data from database
        fun deleteData() {
                val db = this.writableDatabase
                db.delete(TABLE_NAME, null, null)
                //db.delete(TIME_MEASURMENT_TABLE_NAME, null, null)
                db.close()
        }

        fun insertTimeMeasurement(elapsedTime: Int, trailId: Int) {
                val db = this.writableDatabase
                val values = ContentValues()
                values.put("time", elapsedTime)
                values.put("trail_id", trailId)
                db.insert(TIME_MEASURMENT_TABLE_NAME, null, values)
                db.close()
        }

        fun getTimeMeasurementsForTrail(trailId: Int): List<Int> {
                val list = ArrayList<Int>()
                val selectQuery = "SELECT  * FROM $TIME_MEASURMENT_TABLE_NAME WHERE trail_id = $trailId"
                val db = this.readableDatabase
                val cursor = db.rawQuery(selectQuery, null)
                if (cursor.moveToFirst()) {
                        do {
                                val time = cursor.getInt(1)
                                list.add(time)
                        } while (cursor.moveToNext())
                }
                cursor.close()
                db.close()
                return list
        }
}