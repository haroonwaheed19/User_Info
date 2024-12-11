package com.hwm.quiz1mobileapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Fetching the passed data from MainActivity
        val name = intent.getStringExtra("name")
        val degree = intent.getStringExtra("degree")
        val gender = intent.getStringExtra("gender")
        val hobbies = intent.getStringExtra("hobbies")
        val dob = intent.getStringExtra("dob")

        // Initializing TextViews to display the data
        val tvName: TextView = findViewById(R.id.tvName)
        val tvDegree: TextView = findViewById(R.id.tvDegree)
        val tvGender: TextView = findViewById(R.id.tvGender)
        val tvHobbies: TextView = findViewById(R.id.tvHobbies)
        val tvDob: TextView = findViewById(R.id.tvDob)
        val tvAge: TextView = findViewById(R.id.tvAge)
        val tvAgeMessage: TextView = findViewById(R.id.tvAgeMessage)

        // Displaying the received data
        tvName.text = name ?: "Name not provided"
        tvDegree.text = degree ?: "Degree not selected"
        tvGender.text = gender ?: "Gender not selected"
        tvHobbies.text = hobbies ?: "No hobbies selected"
        tvDob.text = dob ?: "Date of Birth not selected"

        // Calculate age if DOB is provided
        if (!dob.isNullOrEmpty()) {
            val age = calculateAge(dob)
            if (age >= 18) {
                tvAge.text = "Age: $age"
                tvAgeMessage.text = "" // No message needed for age >= 18
            } else {
                tvAge.text = "Age: $age"
                tvAgeMessage.text = "Age is less than 18"
            }
        } else {
            tvAge.text = "Age not calculated"
            tvAgeMessage.text = "Invalid Date of Birth"
        }
    }

    private fun calculateAge(dob: String): Int {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US) // Assuming DOB is in dd/MM/yyyy format
        return try {
            val birthDate = dateFormat.parse(dob)
            val today = Calendar.getInstance()
            val birthCalendar = Calendar.getInstance()
            birthCalendar.time = birthDate!!

            var age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

            // Adjust age if today's date is before the birth date
            if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--
            }

            age
        } catch (e: Exception) {
            -1 // Return -1 in case of error
        }
    }
}