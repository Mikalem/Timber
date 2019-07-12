package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.support.design.widget.TextInputEditText
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

/* Handles all functionality related to the ProfileEdit activity */
class ProfileEdit : AppCompatActivity() {

    // Reference to Firebase database (more info: firebase.google.com)
    private val database = FirebaseDatabase.getInstance()
    // Graduating year should not be beyond the current year (with 5-yr leeway)
    private val currentYearPlusFive = Calendar.getInstance().get(Calendar.YEAR) + 5

    /* onCreate runs when activity is loaded */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        setUpProfileHeaderName()
        setUpClickEvents()
        setUpAutoCompleteArrays()
    }

    /* Sets up profile header to become provided full name */
    private fun setUpProfileHeaderName() {
        // Instantiate userID from Firebase login info
        val user = FirebaseAuth.getInstance().currentUser
        val fullName = user?.displayName
        val fullNameRefTV = findViewById<TextView>(R.id.ProjectNameTextView)
        fullNameRefTV.text = fullName
    }

    /* Sets up and handles click events */
    private fun setUpClickEvents() {
        val saveChangesBtn = findViewById<Button>(R.id.save_changes_btn)
        val discardChangesBtn = findViewById<Button>(R.id.discard_changes_btn)

        // Write results to Firebase DB, launch Profile activity
        saveChangesBtn.setOnClickListener {
            Log.d("myTag", "Save Changes clicked!")
            setProfileValues()
        }

        // DO NOT write results to Firebase DB, launch Profile activity
        discardChangesBtn.setOnClickListener {
            Log.d("myTag", "Discard Changes clicked!")
            toProfile()
        }
    }

    private fun setUpAutoCompleteArrays() {
        // Set up adapter for city_array
        val locationTV = findViewById<AutoCompleteTextView>(R.id.input_location_edit)
        val cities: Array<out String> = resources.getStringArray(R.array.city_array)
        ArrayAdapter(this, android.R.layout.simple_list_item_1, cities).also { adapter ->
            locationTV.setAdapter(adapter)
        }
        // Set up adapter for university_array
        val universityTV = findViewById<AutoCompleteTextView>(R.id.input_university_edit)
        val universities: Array<out String> = resources.getStringArray(R.array.university_array)
        ArrayAdapter(this, android.R.layout.simple_list_item_1, universities).also { adapter ->
            universityTV.setAdapter(adapter)
        }
    }

    /* Handles any changes and error checking, then stores the new information in the Firebase DB */
    private fun setProfileValues() {
        // Instantiate userID from Firebase login info
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        // Location references to read from in activity
        val usernameET = findViewById<TextInputEditText>(R.id.input_user_name_edit)
        val username = usernameET.text.toString()
        val professionET = findViewById<TextInputEditText>(R.id.input_profession_edit)
        val profession = professionET.text.toString()
        val burningQET = findViewById<TextInputEditText>(R.id.input_burningq_edit)
        val burningQ = burningQET.text.toString()
        val degreeET = findViewById<TextInputEditText>(R.id.input_degree_edit)
        val degree = degreeET.text.toString()
        val universityET = findViewById<AutoCompleteTextView>(R.id.input_university_edit)
        val university = universityET.text.toString()
        val graduateYearET = findViewById<TextInputEditText>(R.id.input_graduateyear_edit)
        val graduateYear = graduateYearET.text.toString()
        val locationET = findViewById<AutoCompleteTextView>(R.id.input_location_edit)
        val location = locationET.text.toString()

        // Write to in Firebase DB if values are not null && valid
        if (username.isNotEmpty()) {
            writeDB("Username", username, "$uid/Username")
        }
        if (profession.isNotEmpty()) {
            writeDB("Profession", profession, "$uid/Profession")
        }
        if (burningQ.isNotEmpty()) {
            writeDB("Burning Question", burningQ, "$uid/Burning Question")
        }
        if (degree.isNotEmpty()) {
            writeDB("Degree", degree, "$uid/Degree")
        }
        if (university.isNotEmpty()) {
            writeDB("University", university, "$uid/University")
        }
        val yearPattern = "^\\d{4}$"
        if (graduateYear.isNotEmpty()
            && graduateYear.matches(yearPattern.toRegex())
            && graduateYear.toInt() <= currentYearPlusFive){
            writeDB("Graduating Year", graduateYear, "$uid/Graduating Year")
        }
        else if (graduateYear.isNotEmpty()){
            Log.d("myTag", "Year change invalid")
            graduateYearET.requestFocus()
            graduateYearET.error = "The year should be only 4 digits and within the next 5 years\n(or leave empty)"
            return
        }
        if (location.isNotEmpty()) {
            writeDB("Location", location, "$uid/Location")
        }

        // Success, launch Profile, exit ProfileEdit
        toProfile()
    }

    /* Writes the passed info to the Firebase Database */
    private fun writeDB(subject: String, subjVal: String, path: String) {
        val myRef = database.getReference(path)
        myRef.setValue(subjVal)
        Log.d("myTag", "$subject: $subjVal")
    }

    /* Launches Profile activity */
    private fun toProfile() {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        Log.d("myTag", "Switched to Profile activity")
        finish()
    }
}
