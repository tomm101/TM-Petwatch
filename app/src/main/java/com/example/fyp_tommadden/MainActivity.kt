package com.example.fyp_tommadden

import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    lateinit var abcImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Initializing the variable for image button
        abcImageButton = findViewById(R.id.imageButton)

        // Below code is for setting a click listener on the image
        abcImageButton.setOnClickListener {
            // Creating a toast to display the message
            Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
            val url = "https://www.geeksforgeeks.org/"
            // Creating an explicit intent to open the
            // link stored in variable url
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }

}