package com.example.fyp_tommadden

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fyp_tommadden.databinding.ActivityLoginPageBinding
import com.example.fyp_tommadden.databinding.ActivityPetPageBinding

class PetActivityPage : AppCompatActivity() {
    private lateinit var binding: ActivityPetPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.card1.setOnClickListener {
            val intent = Intent(this, DailySessions::class.java)
            startActivity(intent)
        }
        binding.card2.setOnClickListener {
            val intent = Intent(this, TotalActivity::class.java)
            startActivity(intent)
        }
    }
}