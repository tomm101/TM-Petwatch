package com.example.fyp_tommadden


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_tommadden.databinding.ActivityRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class RegisterPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var db: DatabaseReference
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fAuth = FirebaseAuth.getInstance();
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.Registerbtn.setOnClickListener {
            val firstName = binding.Fname.text.toString()
            val lastName = binding.Lname.text.toString()
            val PhoneNo = binding.PhoneNo.text.toString()
            val Email = binding.email.text.toString()
            val Password= binding.password.text.toString()

            fAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(baseContext, "Registration Successful.",
                            Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                    } else {
                        // If register in fails, display a message to the user.
                        Toast.makeText(baseContext, "Registration Unsuccessful",
                            Toast.LENGTH_SHORT).show()
                    }
                }

            db = FirebaseDatabase.getInstance().getReference("User")
            val user = User(firstName,lastName,PhoneNo) // pass data to the user class
            db.child(PhoneNo).setValue(user).addOnSuccessListener {
                binding.Fname.text.clear()
                binding.Lname.text.clear()
                binding.PhoneNo.text.clear()
                binding.email.text.clear()
                binding.password.text.clear()

                Toast.makeText(this, "User Has Successfully Been Registered", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Registration  failed. Please Try Again", Toast.LENGTH_SHORT).show()  }




        }
}}