package com.example.hikingtrails

import Trail
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

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
        private const val TIME_MEASUREMENT_TABLE_NAME = "TimeMeasurement"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        recreateDatabase(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        recreateDatabase(db)
    }


    fun insertData(name: String, description: String, imagePath: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, name)
        values.put(KEY_DESCRIPTION, description)
        values.put(KEY_IMAGE, imagePath)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun saveBitmapToFile(context: Context, bitmap: Bitmap, fileName: String): String {
        val file = File(context.filesDir, fileName)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        return file.absolutePath
    }


    // Function to read data from the database
    fun readData(): List<Trail> {
        val list = ArrayList<Trail>()
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val description = cursor.getString(2)
                val imagePath = cursor.getString(3) // Assuming column index for image path
                val image = if (imagePath != null) BitmapFactory.decodeFile(imagePath) else null
                list.add(Trail(id, name, description, image))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }


    fun insertAccurateData(context: Context) {
        val route1_jpg = BitmapFactory.decodeResource(context.resources, R.drawable.route1)
        val route2_jpg = BitmapFactory.decodeResource(context.resources, R.drawable.route2)
        val route3_jpg = BitmapFactory.decodeResource(context.resources, R.drawable.route3)
        val route4_jpg = BitmapFactory.decodeResource(context.resources, R.drawable.route4)
        val route5_jpg = BitmapFactory.decodeResource(context.resources, R.drawable.route5)

        val route1Path = saveBitmapToFile(context, route1_jpg, "route1.png")
        val route2Path = saveBitmapToFile(context, route2_jpg, "route2.png")
        val route3Path = saveBitmapToFile(context, route3_jpg, "route3.png")
        val route4Path = saveBitmapToFile(context, route4_jpg, "route4.png")
        val route5Path = saveBitmapToFile(context, route5_jpg, "route5.png")

        insertData(
            "Lake Malta Loop",
            "Explore this 6.1-km loop trail near Poznań, Greater Poland.\nGenerally considered an easy route, it takes an average of 1 h 11 min to complete.\nThis is a very popular area for hiking, road biking, and running, so you'll likely encounter other people while exploring. The trail is open year-round and is beautiful to visit anytime.\nDogs are welcome, but must be on a leash.",
            route1Path
        )
        insertData(
            "Kuznie-Kosicelec",
            "Head out on this 16.7-km loop trail near Zakopane, Lesser Poland.\nGenerally considered a challenging route, it takes an average of 7 h 8 min to complete.\nThis is a very popular area for hiking, so you'll likely encounter other people while exploring. The best times to visit this trail are June through October.\nYou'll need to leave pups at home — dogs aren't allowed on this trail.\nThis trail is an ideal option for a day hike in the Tatra Mountains. The trail begins in Kuźnice at the ticket offices of the Tatra National Park.\n" +
                    "\n" +
                    "The first section of the route leads through the forest to reveal breathtaking views that will accompany us for the rest of the hike. On Hala Gąsienicowa there is the PTTK Murowaniec Mountain Hut, which is open all year round. The section of the route leading to the summit is very challenging, we will encounter steep slopes, rock thresholds, rock steps and large boulders.",
            route2Path
        )
        insertData(
            "Stiffkey-Cockthrope Circular",
            "Experience this 8.9-km loop trail near Wells-next-the-Sea, Norfolk\nGenerally considered a moderately challenging route, it takes an average of 1 h 49 min to complete.\nThis trail is great for hiking, running, and walking, and it's unlikely you'll encounter many other people while exploring.\nThis route takes you along the Warham Salt Marshes with views looking out across the North Sea. This is a pretty and quiet area for walking and it is likely that you will see birds and wildlife in the marshes. The walk is partially on paved roads with much of it being on trails. There is a small hill near Cockthorpe but the route is for the most part very flat.",
            route3Path
        )
        insertData(
            "Aspy Trail",
            "Experience this 9.2-km out-and-back trail near Big Intervale Cape North, Nova Scotia.\nGenerally considered a moderately challenging route, it takes an average of 3 h 0 min to complete.\nThis is a popular trail for birding, hiking, and running, but you can still enjoy some solitude during quieter times of day. The trail is open year-round and is beautiful to visit anytime.\nDogs are welcome, but must be on a leash.\nThis route begins near Beulach Ban Falls and then goes along Aspy Trail. This land was once used for farming and logging, but is now a regenerating forest that is mixing new with old growth. \n" +
                    "\n" +
                    "It is possible to see and hear barred and great horned owls in the area. There are several stream crossings on the way, so bringing waterproof hiking boots is recommended.",
            route4Path
        )
        insertData(
            "Parker Lake Trail",
            "Head out on this 5.8-km out-and-back trail near June Lake, California.\nGenerally considered a moderately challenging route, it takes an average of 1 h 42 min to complete.\nThis is a very popular area for birding, hiking, and running, so you'll likely encounter other people while exploring. The best times to visit this trail are May through October.\nDogs are welcome and may be off-leash in some areas.\nThis is a trail through beautiful wilderness to a pristine alpine lake. There are a few steep, rocky ascents in direct sunlight toward the beginning. You will hike along the creek draining Parker Lake, first starting well above it and only hearing its cascade, before walking right along its banks upon the approach to the lake. The vegetation changes accordingly, first as one walks across wildflower-covered meadows and then between tall coniferous trees along the creek. The lake is of crystal-clear water and surrounded by Sierra peaks - the same peaks of which the opposite side forms the boundary of Yosemite National Park.\n" +
                    "\n" +
                    "There are beautiful wildflowers in the late spring and early summer. This is also a popular spot to view colorful foliage in the fall.",
            route5Path
        )
    }


    private fun getBitmapAsByteArray(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) return null
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        return stream.toByteArray()
    }

    private fun recreateDatabase(db: SQLiteDatabase?) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TIME_MEASUREMENT_TABLE_NAME")
        createBaseTable(db)
        createTimeMeasurementTable(db)
    }

    private fun createBaseTable(db: SQLiteDatabase?) {
        val CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS $TABLE_NAME ("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_NAME TEXT,"
                + "$KEY_DESCRIPTION TEXT,"
                + "$KEY_IMAGE TEXT)") // Path to the image
        db?.execSQL(CREATE_TABLE)
    }


    private fun createTimeMeasurementTable(db: SQLiteDatabase?) {
        val CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS $TIME_MEASUREMENT_TABLE_NAME ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "time INTEGER,"
                + "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "trail_id INTEGER,"
                + "FOREIGN KEY(trail_id) REFERENCES $TABLE_NAME(id))")
        db?.execSQL(CREATE_TABLE)
    }

    fun deleteData() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        //db.delete(TIME_MEASUREMENT_TABLE_NAME, null, null)
        db.close()
    }

    fun insertTimeMeasurement(elapsedTime: Int, trailId: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("time", elapsedTime)
        values.put("trail_id", trailId)
        db.insert(TIME_MEASUREMENT_TABLE_NAME, null, values)
        db.close()
    }

    fun getTimeMeasurementsForTrailWithTimestamp(trailId: Int): List<Pair<Int, Long>> {
        val list = ArrayList<Pair<Int, Long>>()
        val selectQuery =
            "SELECT time, timestamp FROM $TIME_MEASUREMENT_TABLE_NAME WHERE trail_id = $trailId ORDER BY id"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val time = cursor.getInt(0)
                val timestamp = cursor.getString(1)
                val timestampLong =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp)?.time ?: 0
                list.add(Pair(time, timestampLong))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }
}
