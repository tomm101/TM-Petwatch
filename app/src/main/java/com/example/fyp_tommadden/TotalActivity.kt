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
    private lateinit var dates: List<String>

    // Define the BarChart
    private lateinit var mBarChart: BarChart

    // Get the current date
    private val mCurrentDate = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)

        // Set up the BarChart
        mBarChart = findViewById(R.id.barChart) // Replace with the ID of your BarChart view


        val query: Query = mDatabase.getReference("Timer").child("3CsRkX71KbdQcgQwMHAOFg5fwiB2").child(mCurrentDate)


        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val values = ArrayList<BarEntry>()

                for (dateSnapshot in dataSnapshot.children) {
                    val value = dateSnapshot.getValue(Float::class.java) ?: 0f
                    values.add(BarEntry(values.size.toFloat(), value))
                }
                System.out.println(values)
                val dataSet = BarDataSet(values, "Values")
                val barData = BarData(dataSet)

                mBarChart.data = barData
                mBarChart.invalidate()

               // dates = ((dataSnapshot.value as HashMap<*, *>).keys).toList() as List<String>

                }




            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that may occur
            }
        })
    }
}