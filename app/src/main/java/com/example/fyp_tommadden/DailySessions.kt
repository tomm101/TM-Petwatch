package com.example.fyp_tommadden

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
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
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.properties.Delegates
import kotlin.time.Duration.Companion.seconds

class DailySessions : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var chart: LineChart
    private var lastXValue: Float = 0f
    private lateinit var value : String
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
                    var value1 = data.child("finalTimer").value
                    var value = value1.toString()
                   // timerValue = value.toLong()
                    if (date != null) {
                        val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
                        val test = ZonedDateTime.parse(date, formatter)
                        dataList.add(Entry(test.toInstant().epochSecond.toFloat(),value.toFloat()))
                        lastXValue = test.toInstant().epochSecond.toFloat()
                    }
                }

                // Step 6: Initialize the chart view
                val chartView = findViewById<LineChart>(R.id.chartView)
                val dataSet = LineDataSet(dataList, "Activity Session Time")
                dataSet.mode = LineDataSet.Mode.LINEAR
                dataSet.setDrawHighlightIndicators(true)
                val data = LineData(dataSet)
                chartView.animateX(1200, Easing.EaseInSine)
                chartView.xAxis.granularity = 30F
                chartView.data = data
                chartView.description.text = "Activity Sessions"
                chartView.xAxis.valueFormatter =MyXAxisValueFormatter()
                chartView.axisRight.isEnabled= false
                chartView.axisLeft.valueFormatter = MyYAxisValueFormatter()
                chartView.axisRight.valueFormatter = MyYAxisValueFormatter()

                // Step 7: Bind the chart view to the data
               // chartView.axisLeft.axisMinimum = 0f
                chartView.invalidate()

                // Step 8: Scroll to the last entry
               // chartView.moveViewToX(lastXValue)

                // Set value formatter for the values displayed on the graph
                val valueFormatter = object : ValueFormatter() {
                     override fun getFormattedValue(value: Float): String {
                        val timer = value /1000
                        val hours = value.toInt() / 60
                        val minutes = value.toInt() % 60
                        return String.format("%02d:%02d", hours, minutes)
                    }
                }

                dataSet.valueFormatter = valueFormatter
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
            //val parsed = min(value.toLong(), 600)
            // Limit the maximum value to 10
            //return parsed.absoluteValue.toString()
            val yax = " "
            return yax
        }
    }


}

