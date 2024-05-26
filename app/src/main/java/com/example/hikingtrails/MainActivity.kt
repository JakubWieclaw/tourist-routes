package com.example.hikingtrails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.hikingtrails.ui.ListDetailPaneScaffoldParts
import com.example.hikingtrails.ui.theme.HikingTrailsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DatabaseHandler(this)
        // delete data from the database and then recreate it
        // db.deleteData()
        // Insert accurate data into the database
        // db.insertAccurateData(context = this)
        val trails = db.readData()
        setContent {
            HikingTrailsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListDetailPaneScaffoldParts(trails, this)
                }
            }
        }
    }
}