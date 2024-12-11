package com.hwm.quiz1mobileapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private var dateOfBirth: String = "" // Initialize with an empty string

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText: EditText = findViewById(R.id.etName)
        val spinner: Spinner = findViewById(R.id.sDegree)
        val radioGroup: RadioGroup = findViewById(R.id.rgGender)
        val cricketCheckBox: CheckBox = findViewById(R.id.cbCricket)
        val hockeyCheckBox: CheckBox = findViewById(R.id.cbHockey)
        val tennisCheckBox: CheckBox = findViewById(R.id.cbTennis)
        val badmintonCheckBox: CheckBox = findViewById(R.id.cbBadminton)
        val dateButton: Button = findViewById(R.id.btnPickDate)
        val dateTextView: TextView = findViewById(R.id.tvDob) // Added dynamic DOB display
        val enterButton: Button = findViewById(R.id.btnEnter)
        val clearButton: Button = findViewById(R.id.btnClear)

        // Spinner setup
        val degrees = arrayOf("Select Degree", "Bachelor", "Master", "PhD")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, degrees)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // DatePicker setup
        dateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    dateOfBirth = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    dateTextView.text = "Selected Date: $dateOfBirth" // Update TextView dynamically
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        // Enter button click listener
        enterButton.setOnClickListener {
            // Get the entered name
            val name = nameEditText.text.toString()

            // Get the selected degree
            val selectedDegree = spinner.selectedItem.toString()

            // Get the selected gender
            val selectedGenderId = radioGroup.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                "Not selected"
            }

            // Get hobbies
            val hobbies = mutableListOf<String>()
            if (cricketCheckBox.isChecked) hobbies.add("Cricket")
            if (hockeyCheckBox.isChecked) hobbies.add("Hockey")
            if (tennisCheckBox.isChecked) hobbies.add("Tennis")
            if (badmintonCheckBox.isChecked) hobbies.add("Badminton")

            // Check if all required fields are filled
            if (name.isEmpty() || selectedDegree == "Select Degree" || dateOfBirth.isEmpty()) {
                Toast.makeText(this, "Please fill in all the details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create an intent to pass data to the next activity
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("name", name)
            intent.putExtra("degree", selectedDegree)
            intent.putExtra("gender", gender)
            intent.putExtra("hobbies", hobbies.joinToString(", "))
            intent.putExtra("dob", dateOfBirth)
            startActivity(intent)
        }


        clearButton.setOnClickListener {
            // Clear the name input field
            nameEditText.text.clear()

            // Reset the spinner to its initial state
            spinner.setSelection(0)

            // Reset the RadioGroup (Gender) to the default checked state
            radioGroup.check(R.id.rbMale)

            // Uncheck all the CheckBoxes (Hobbies)
            cricketCheckBox.isChecked = false
            hockeyCheckBox.isChecked = false
            tennisCheckBox.isChecked = false
            badmintonCheckBox.isChecked = false

            // Reset the Date of Birth to its default state
            dateOfBirth = ""
            dateTextView.text = "Date not selected"

            // Show a confirmation message
            Toast.makeText(this, "All fields have been cleared", Toast.LENGTH_SHORT).show()
        }

    }
}