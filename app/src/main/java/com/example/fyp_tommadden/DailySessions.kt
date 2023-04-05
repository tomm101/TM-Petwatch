package com.example.fyp_tommadden

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DailySessions : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var chart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Step 2: Create a reference to the Firebase database
        databaseRef = FirebaseDatabase.getInstance().getReference("Timer")

        // Step 3: Attach a listener to the reference to retrieve the data
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Step 4: Parse the retrieved data and store it in an ArrayList
                val dataList = ArrayList<Entry>()
                for (data in dataSnapshot.children) {
                    val date = data.key
                    val value = data.child("finalTimer").value.toString()
                    if (date != null) {
                        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
                        val dateObj = dateFormat.parse(date)
                        val timestamp = dateObj.time
                        dataList.add(Entry(timestamp.toFloat(),value.toFloat()))
                    }
                }

                // Step 6: Initialize the chart view
                val chartView = findViewById<LineChart>(R.id.chartView)
                val dataSet = LineDataSet(dataList, "Data Set")
                val data = LineData(dataSet)
                chartView.data = data
                chartView.description.text = "Activity Sessions"
                chartView.xAxis.valueFormatter =MyXAxisValueFormatter()

                // Step 7: Bind the chart view to the data
                chartView.invalidate()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "onCancelled: " + error.message)
            }
        })
    }
    // Custom X-axis value formatter to format the dates
    inner class MyXAxisValueFormatter : ValueFormatter() {
        private val mFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        override fun getFormattedValue(value: Float): String {
            return mFormat.format(Date(value.toLong()))
        }
    }}

