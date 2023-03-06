package com.example.fyp_tommadden


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        // reference to button
        val regbutton = findViewById<View>(R.id.Registerbtn) as Button

        regbutton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val i = Intent(applicationContext, LoginPage::class.java)
                startActivity(i)
            }
        })

// set on-click listener
//       regbutton.setOnClickListener {
//            Toast.makeText(this@RegisterPage, "You clicked me.", Toast.LENGTH_SHORT).show()
//        }
    }
}