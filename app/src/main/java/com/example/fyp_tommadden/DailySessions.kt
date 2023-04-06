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
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class DailySessions : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var chart: LineChart
    private var lastXValue: Float = 0f
    private lateinit var timerValue: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_sessions)

        // Step 2: Create a reference to the Firebase database
        databaseRef = FirebaseDatabase.getInstance().getReference("Timer")

        // Step 3: Attach a listener to the reference to retrieve the data
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Step 4: Parse the retrieved data and store it in an ArrayList
                val dataList = ArrayList<Entry>()
                for (data in dataSnapshot.children) {
                    val date = data.child("currentDate").value.toString()
                    var value = data.child("finalTimer").value.toString()
                    timerValue = value
                    if (date != null) {
                        val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
                        val test = ZonedDateTime.parse(date, formatter)
                        dataList.add(Entry(test.toInstant().epochSecond.toFloat(),value.toFloat()))
                        lastXValue = test.toInstant().epochSecond.toFloat()
                    }
                }

                // Step 6: Initialize the chart view
                val chartView = findViewById<LineChart>(R.id.chartView)
                val dataSet = LineDataSet(dataList, "Data Set")
                dataSet.mode = LineDataSet.Mode.LINEAR
                dataSet.setDrawHighlightIndicators(false)
                val data = LineData(dataSet)
                chartView.data = data
                chartView.description.text = "Activity Sessions"
                chartView.xAxis.valueFormatter =MyXAxisValueFormatter()
                chartView.axisLeft.valueFormatter = MyYAxisValueFormatter()
                chartView.axisRight.valueFormatter = MyYAxisValueFormatter()

                // Step 7: Bind the chart view to the data
               // chartView.axisLeft.axisMinimum = 0f
                chartView.invalidate()

                // Step 8: Scroll to the last entry
                chartView.moveViewToX(lastXValue)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "onCancelled: " + error.message)
            }
        })
    }
    // Custom X-axis value formatter to format the dates
    inner class MyXAxisValueFormatter : ValueFormatter() {
        private val mFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
        private val calendar = Calendar.getInstance()
        private val today = calendar.time

        override fun getFormattedValue(value: Float): String {
           // return mFormat.format(Date(value.toLong()))
            calendar.time = today
            calendar.add(Calendar.DAY_OF_MONTH, value.toInt())
            return if (value == 0f) {
                // For the first value, return today's date
                mFormat.format(today)
            } else {
                // For subsequent values, increment the date by the value in days
                mFormat.format(calendar.time)
            }
        }
    }
    inner class MyYAxisValueFormatter : ValueFormatter() {

        override fun getFormattedValue(value: Float): String {
            val incrementedValue = value + 1
            // Limit the maximum value to 10
            val formattedValue = if (incrementedValue <= 10) incrementedValue.toString() else ""
            return formattedValue
//            return finalTimer
        }
    }


}

