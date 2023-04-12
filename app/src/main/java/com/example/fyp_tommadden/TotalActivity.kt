package com.example.fyp_tommadden

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TotalActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)

        barChart = findViewById(R.id.barChart)

        // Get today's date
        val currentDate = SimpleDateFormat("dd/MM/yyyy").format(Date())

        // Initialize Firebase database
        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Timer")

        // Query the database to get data for today's date
        val query = databaseReference.orderByChild("currentDate").equalTo(currentDate)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Create an ArrayList to store bar chart entries
                val entries: ArrayList<BarEntry> = ArrayList()

                // Loop through the dataSnapshot to parse the data and add it to entries
                for (snapshot in dataSnapshot.children) {
                    val value = snapshot.child("FinalTimer").getValue(Long::class.java)
                    // Add the value to entries
                    if (value != null) {
                        entries.add(BarEntry(entries.size.toFloat(), value.toFloat()))
                    }
                }

                // Create a BarDataSet from the entries
                val dataSet = BarDataSet(entries, "Data Label")
                dataSet.color = getColor(R.color.LightBlue)

                // Create a BarData object from the dataSet
                val barData = BarData(dataSet)

                // Set the data to the bar chart
                barChart.data = barData

                // Refresh the chart
                barChart.invalidate()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error here
            }
        })
    }
}

