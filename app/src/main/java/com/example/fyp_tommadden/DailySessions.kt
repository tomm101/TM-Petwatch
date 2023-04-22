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
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DailySessions : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var chart: LineChart
    private var lastXValue: Float = 0f
    private lateinit var timerValue: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_sessions)

        // Step 2: Create a reference to the Firebase database
        databaseRef = FirebaseDatabase.getInstance().getReference("Timer").child("GdEnp5oaJaSABIesTs18u5dj4m53")

        val format = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
        // Step 3: Attach a listener to the reference to retrieve the data
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Step 4: Parse the retrieved data and store it in an ArrayList
                val dataList = ArrayList<Entry>()
                var maxTimestamp: Float = 0f
                if(dataSnapshot.value == null){
                    return
                }
                for (data in dataSnapshot.value as HashMap<*, *>) {
                    for (time in data.value as HashMap<*, *>) {
                        val timestamp = LocalDate.parse(data.key as String).atStartOfDay(ZoneOffset.UTC)
                            .toInstant().toEpochMilli().toFloat()
                        dataList.add(Entry(timestamp, time.value.toString().toFloat()))
                        maxTimestamp = maxOf(maxTimestamp, timestamp)
                    }
                    lastXValue = maxTimestamp

//                    timerValue = (value as Long).toDuration(DurationUnit.MILLISECONDS).toString()
                    //                        val formatter = DateTimeFormatter.ofPattern(
//                            "EEE MMM dd HH:mm:ss z yyyy",
//                            Locale.getDefault()
//                        )
//                    val test = ZonedDateTime.parse(date, formatter)
//                    dataList.add(Entry(test.toInstant().epochSecond.toFloat(), value.toFloat()))
//                    lastXValue = test.toInstant().epochSecond.toFloat()
                }

                System.out.println(dataList)

                // Step 6: Initialize the chart view
                val chartView = findViewById<LineChart>(R.id.chartView)
                val dataSet = LineDataSet(dataList, "Data Set")
                dataSet.mode = LineDataSet.Mode.LINEAR
                dataSet.setDrawHighlightIndicators(false)
                val data = LineData(dataSet)
                chartView.xAxis.granularity = 30F
                chartView.data = data
                chartView.description.text = "Activity Sessions"
                chartView.xAxis.valueFormatter = MyXAxisValueFormatter()
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
    inner class MyXAxisValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val simpleDateFormat = SimpleDateFormat("dd/MM", Locale.ENGLISH)
            return simpleDateFormat.format(value)
        }
    }


    inner class MyYAxisValueFormatter : ValueFormatter() {

        override fun getFormattedValue(value: Float): String {
            // Limit the maximum value to 10
            return " "
//            return finalTimer
        }
    }


}