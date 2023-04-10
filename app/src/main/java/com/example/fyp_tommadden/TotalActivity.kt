package com.example.fyp_tommadden

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TotalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)
//
//
//        // Create a Firebase database reference
//        val databaseReference = FirebaseDatabase.getInstance().reference.child("Timer")
//
//// Add a listener to retrieve data
//        databaseReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // Create a HashMap to store the sum of values associated with each date
//                val dateValueMap = HashMap<String, Int>()
//
//                // Iterate through the snapshot to calculate the sum of values for each date
//                for (childSnapshot in snapshot.children) {
//                    val timer = childSnapshot.getValue(Timer::class.java)
//                    timer?.let {
//                        val currentDate = it.currentDate
//                        val value = it.value
//                        if (dateValueMap.containsKey(currentDate)) {
//                            dateValueMap[currentDate] = dateValueMap[currentDate]!! + value
//                        } else {
//                            dateValueMap[currentDate] = value
//                        }
//                    }
//                }
//
//                // Call a function to display the data in a table
//                displayDataInTable(dateValueMap)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle error, if any
//            }
//        })
//
   }
}