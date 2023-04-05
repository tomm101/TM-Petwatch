package com.example.fyp_tommadden

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fyp_tommadden.databinding.ActivityConnectionPageBinding
import com.example.fyp_tommadden.databinding.ActivityLoginPageBinding

class ConnectionPage : AppCompatActivity() {
    private lateinit var binding: ActivityConnectionPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}