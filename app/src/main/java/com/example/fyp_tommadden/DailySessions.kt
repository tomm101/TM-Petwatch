package com.example.fyp_tommadden

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.combine
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DailySessions : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var chart: LineChart

    private var lastXValue: Float = 0f
    private lateinit var timerValue: String
    private lateinit var allKeys: List<String>
    private val combinedTimes = mutableMapOf<Int, Float>()
    val user = FirebaseAuth.getInstance().currentUser?.uid
        ?: throw Exception("User is not signed in. Please Sign in")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_sessions)

        //Create a reference to the Firebase database
        databaseRef = FirebaseDatabase.getInstance().getReference("Timer")
            .child(user)

        val format = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
        //Attach a listener to the reference to retrieve the data
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Parse the retrieved data and store it in an ArrayList
                val dataList = ArrayList<Entry>()
                if (dataSnapshot.value == null) {
                    return
                }

                allKeys = ((dataSnapshot.value as HashMap<*, *>).keys).toList() as List<String> //create a list of all the date instances in database
                for (i in allKeys.indices) {
                    combinedTimes[i] = 0f
                }

                for (data in dataSnapshot.value as HashMap<*, *>) {
                    for (time in data.value as HashMap<*, *>) {
                        val currentTimes = combinedTimes[allKeys.indexOf(data.key)]!!
                        combinedTimes[allKeys.indexOf(data.key)] = currentTimes + time.value as Long
                    }
                }

                combinedTimes.forEach { entry -> dataList.add(
                    Entry(entry.key.toFloat(), entry.value) //get timer value for each item in the Dates list
                    )
                }

                //Initialize the chart view
                val chartView = findViewById<LineChart>(R.id.chartView)
                val dataSet = LineDataSet(dataList, "Data Set")
                dataSet.mode = LineDataSet.Mode.LINEAR
                dataSet.setDrawHighlightIndicators(false)
                val data = LineData(dataSet)
                data.setValueFormatter(MyYAxisValueFormatter())
                chartView.data = data
                chartView.xAxis.labelCount = allKeys.size
                chartView.description.text = "Activity Sessions"
                chartView.xAxis.valueFormatter = MyXAxisValueFormatter()
                chartView.axisLeft.valueFormatter = MyYAxisValueFormatter()
                chartView.axisRight.valueFormatter = MyYAxisValueFormatter()

                chartView.invalidate()

                //Scroll to the last entry
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
            val fullDate = LocalDate.parse(allKeys[value.toInt()])
            return fullDate.format(DateTimeFormatter.ofPattern("dd/MM"))
        }
    }

    inner class MyYAxisValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.toDouble().toDuration(DurationUnit.MILLISECONDS).absoluteValue.toString()
        }
    }


}