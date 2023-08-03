package com.example.authassignment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.authassignment.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var otp: String
    private lateinit var number: String
    private lateinit var email: String
    private lateinit var password: String
    private val auth  = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checksmspermission()

        binding.textView4.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.button.setOnClickListener {
            if (checkValidation().isNullOrEmpty()) {

                number = binding.etPhone.text.toString()
                email = binding.editTextTextPersonName.text.toString()
                password = binding.editTextTextPersonName2.text.toString()
                otp = generateOtp()
                sendOtp()
                //save user info
                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    Toast.makeText(this, "Successfully Register", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("otp", otp)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {

                }
            } else {
                val result = checkValidation()
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun checksmspermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), 100)
        }
    }

    private fun sendOtp() {
        val smsManager = SmsManager.getDefault()
        var message : String? = null
        message = "Otp - $otp"
        smsManager.sendTextMessage(
            number,
            null,
            message,
            null,
            null
        )
    }

    private fun generateOtp(): String {
        val randomPin = (Math.random() * 9000).toInt() + 1000
        return randomPin.toString()
    }

    private fun checkValidation(): String? {
        if (binding.editTextTextPersonName3.text.toString().isEmpty() &&
            binding.editTextTextPersonName.text.toString().isEmpty() &&
            binding.editTextTextPersonName2.text.toString().isEmpty()
        )
            return "All Field Must Be Filled"
        if (binding.editTextTextPersonName3.text.toString().isEmpty())
            return "Fill Your Name"
        if (binding.editTextTextPersonName.text.toString().isEmpty())
            return "Fill Your Email"
        if (binding.editTextTextPersonName2.text.toString().isEmpty())
            return "Fill Your Password"
        if (binding.etPhone.text.toString().length<10)
            return "Phone Number is not valid"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextPersonName.text.toString())
                .matches()
        )
            return "Email is not valid"
        else return null
    }
}