package com.example.authassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.authassignment.databinding.ActivityLoginBinding
import com.example.authassignment.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView4.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.tvForgot.setOnClickListener {
            auth.sendPasswordResetEmail(binding.editTextTextPersonName.text.toString()).addOnSuccessListener {
                Toast.makeText(this, "Check Your Email, reset password Link has been sent", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        }
        binding.button.setOnClickListener {
            if (checkValidation().isNullOrEmpty())
                auth.signInWithEmailAndPassword(
                    binding.editTextTextPersonName.text.toString(),
                    binding.editTextTextPersonName2.text.toString()
                ).addOnSuccessListener {
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, WelcomeActivity::class.java))
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            /*var user = SharedPrefManager(this).getUserInfo()
                if (binding.editTextTextPersonName.text.toString() == user[0] &&
                    binding.editTextTextPersonName2.text.toString()== user[1]){
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, WelcomeActivity::class.java))
                    finish()
                }*/

        } /*else {
            val result = checkValidation()
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
        }
    }*/

    }

    private fun checkValidation(): String? {
        if (
            binding.editTextTextPersonName.text.toString().isEmpty() &&
            binding.editTextTextPersonName2.text.toString().isEmpty()
        )
            return "All Field Must Be Filled"
        if (binding.editTextTextPersonName.text.toString().isEmpty())
            return "Fill Your Email"
        if (binding.editTextTextPersonName2.text.toString().isEmpty())
            return "Fill Your Password"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextPersonName.text.toString())
                .matches()
        )
            return "Email is not valid"
        else return null
    }
}