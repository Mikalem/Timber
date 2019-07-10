package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.support.design.widget.TextInputEditText
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/* Handles all functionality related to the ProfileEdit activity */
class ProfileEdit : AppCompatActivity() {

    // Reference to Firebase database (more info: firebase.google.com)
    private val database = FirebaseDatabase.getInstance()

    /* onCreate runs when activity is loaded */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        setUpClickEvents()
        autoCompleteArrays()
    }

    /* Sets up and handles click events */
    private fun setUpClickEvents() {
        val saveChangesBtn = findViewById<Button>(R.id.save_changes_btn)
        val discardChangesBtn = findViewById<Button>(R.id.discard_changes_btn)

        // Write results to Firebase DB, launch Profile activity
        saveChangesBtn.setOnClickListener {
            Log.i("myTag", "Save Changes clicked!")
            setProfileValues()
            toProfile()
        }

        // DO NOT write results to Firebase DB, launch Profile activity
        discardChangesBtn.setOnClickListener {
            Log.i("myTag", "Discard Changes clicked!")
            toProfile()
        }
    }

    private fun autoCompleteArrays() {
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

    /* Handles any changes and stores the new information in the Firebase DB */
    private fun setProfileValues() {
        // Instantiate userID from Firebase login info
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val fullName = user?.displayName

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

        // Write to in Firebase DB if values aren't null
        if (fullName != null) {
            val fullNameRef = database.getReference("$uid/Full Name")
            fullNameRef.setValue(fullName)
            Log.i("myTag", "Full Name: $fullName")
        }
        if (username.isNotEmpty()) {
            val usernameRef = database.getReference("$uid/Username")
            usernameRef.setValue(username)
            Log.i("myTag", "Username: $username")
        }
        if (profession.isNotEmpty()) {
            val professionRef = database.getReference("$uid/Profession")
            professionRef.setValue(profession)
            Log.i("myTag", "Profession: $profession")
        }
        if (burningQ.isNotEmpty()) {
            val burningQRef = database.getReference("$uid/Burning Question")
            burningQRef.setValue(burningQ)
            Log.i("myTag", "Burning Question: $burningQ")
        }
        if (degree.isNotEmpty()) {
            val degreeRef = database.getReference("$uid/Degree")
            degreeRef.setValue(degree)
            Log.i("myTag", "Degree: $degree")
        }
        if (university.isNotEmpty()) {
            val universityRef = database.getReference("$uid/University")
            universityRef.setValue(university)
            Log.i("myTag", "University: $university")
        }
        if (graduateYear.isNotEmpty()) {
            val graduateYearRef = database.getReference("$uid/Graduating Year")
            graduateYearRef.setValue(graduateYear)
            Log.i("myTag", "Graduating Year: $graduateYear")
        }
        if (location.isNotEmpty()) {
            val locationRef = database.getReference("$uid/Location")
            locationRef.setValue(location)
            Log.i("myTag", "Location: $location")
        }
    }

    /* Launches Profile activity */
    private fun toProfile() {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        Log.i("myTag", "Switched to Profile activity")
    }
}
