package com.example.fyp_tommadden

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

    // Define the Firebase database reference
    private val mDatabase = FirebaseDatabase.getInstance()

    // Define the BarChart
    private lateinit var mBarChart: BarChart

    // Get the current date
    private val mCurrentDate = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)

        // Set up the BarChart
        mBarChart = findViewById(R.id.barChart) // Replace with the ID of your BarChart view

        // Set up the Firebase database reference with a query for today's date
        val query: Query =
            mDatabase.getReference("Timer").child("user")
                .orderByChild("currentDate").equalTo(mCurrentDate)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Create an ArrayList to store the bar chart entries
                val barEntries = ArrayList<BarEntry>()

                // Loop through each child snapshot (each entry for today's date)
                for (childSnapshot in dataSnapshot.children) {
                    // Retrieve the value as a float
                    val value =
                        childSnapshot.child("finalTimer").getValue(Float::class.java)

                    // Get the x-value as the index of the child snapshot (0, 1, 2, ...)
                    val xValue = dataSnapshot.children.indexOf(childSnapshot).toFloat()

                    // Create a BarEntry with the x-value and the y-value
                    value?.let { barEntries.add(BarEntry(xValue, it)) }
                }

                // Create a BarDataSet with the retrieved bar entries
                val barDataSet = BarDataSet(barEntries, "Data for $mCurrentDate")

                // Create a BarData object with the barDataSet
                val barData = BarData(barDataSet)

                // Set the data to the BarChart
                mBarChart.data = barData

                // Update the chart
                mBarChart.invalidate()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that may occur
            }
        })
    }
}