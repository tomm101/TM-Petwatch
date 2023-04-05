package com.example.fyp_tommadden

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock


import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var abcImageButton: ImageButton
    lateinit var petButton: ImageButton
    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initializing the variable for image button
        abcImageButton = findViewById(R.id.imageButton)
        petButton = findViewById(R.id.petBtn)

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

        petButton.setOnClickListener {
            val intent = Intent(this, PetActivityPage::class.java)
            startActivity(intent)
        }

        val timer = findViewById<Chronometer>(R.id.timer)
        val textView = findViewById<TextView>(R.id.timerText)
        var timerRunning = false

        //access the button using id
        val startbtn = findViewById<Button>(R.id.start)
        startbtn?.setOnClickListener(object : View.OnClickListener {

           // var isWorking = false
            var timerResult: Long = 0


            override fun onClick(v: View) {
                if (!timerRunning) {
                    timer.base = SystemClock.elapsedRealtime()
                    timer.start()
                    timerRunning = true
                    startbtn.setText(R.string.stop)
                    Toast.makeText(this@MainActivity, R.string.working, Toast.LENGTH_SHORT).show()
                }

                else {
                    timer.stop()
                    timerResult = SystemClock.elapsedRealtime() - timer.base
                    val seconds = timerResult / 1000
                    val finalTimer = "%02d:%02d".format(seconds / 60, seconds % 60)
                    val currentDate = Calendar.getInstance().time

                    val Date = currentDate.toString()
                   // timerResult = System.currentTimeMillis()
                    //timer.base = SystemClock.elapsedRealtime() // Reset the timer to 0
                    timerRunning = false
                    startbtn.setText(R.string.start)
                    textView?.text = finalTimer.toString()
                    Toast.makeText(this@MainActivity, R.string.stopped, Toast.LENGTH_SHORT).show()

                    db = FirebaseDatabase.getInstance().getReference("Timer")
                    val timer = Timer(finalTimer, Date) // pass data to the user class

                    db.child(Date).setValue(timer).addOnSuccessListener {
                        Toast.makeText(this@MainActivity, "Saved", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this@MainActivity, "Save Failed", Toast.LENGTH_SHORT).show()  }



            }
        }
})}}

