package com.example.authassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.authassignment.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val otp = intent.getStringExtra("otp")

        binding.button.setOnClickListener {
            if (binding.pinview.text.toString() == otp){
                Toast.makeText(this, "Succesfully Registered", Toast.LENGTH_LONG).show()
            }
        }

    }
}