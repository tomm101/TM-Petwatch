package com.example.fyp_tommadden

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
//import com.example.bluetoothtesting.R
//import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.widget.ImageButton

class ConnectionPage : AppCompatActivity() {
    lateinit var homeButton: ImageButton
    @SuppressLint("MissingPermission", "WrongViewCast", "MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection_page)
            homeButton = findViewById(R.id.homeButton)
            homeButton.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)}

        // Declaring the textView for name from the layout file
        val Name = findViewById<TextView>(R.id.BLEdevice)

        // Declaring the textView for MAC ID from the layout file
        val Mac = findViewById<TextView>(R.id.BleMac)

        // Declaring the button from the layout file
        val btn = findViewById<ImageButton>(R.id.scanButton)

        // Initializing the Bluetooth Adapter
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bAdapter = bluetoothManager.adapter

        // Button Action when clicked
        btn.setOnClickListener {

            // Checks if Bluetooth Adapter is present
            if (bAdapter == null) {
                Toast.makeText(applicationContext, "Bluetooth Not Supported", Toast.LENGTH_SHORT).show()
            } else {
                // Arraylist of all the bonded (paired) devices
                val pairedDevices = bAdapter.bondedDevices
                if (pairedDevices.size > 0) {
                    for (device in pairedDevices) {

                        // get the device name
                        val deviceName = device.name

                        // get the mac address
                        val macAddress = device.address

                        // append in the two separate views
                        Name.append("$deviceName\n")
                        Mac.append("$macAddress\n")
                    }
                }
            }
        }
    }
}
