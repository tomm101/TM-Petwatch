package com.example.fyp_tommadden

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fyp_tommadden.databinding.ActivityLoginPageBinding
import com.example.fyp_tommadden.databinding.ActivityRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fAuth = FirebaseAuth.getInstance();
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // In the Kotlin code for the first activity

        binding.Regbtn.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener{
            val Email = binding.email.text.toString()
            val Password= binding.password.text.toString()

            fAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Login Successful.",
                            Toast.LENGTH_SHORT).show()
                        val connectIntent = Intent(this, ConnectionPage::class.java)
                        startActivity(connectIntent)
                        // Sign in success, update UI with the signed-in user's information

                    } else {
                        Toast.makeText(baseContext, "Login failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        }




    }
}