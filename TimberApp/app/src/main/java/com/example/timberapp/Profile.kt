package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/* Handles all functionality related to the Profile (view) activity */
class Profile : AppCompatActivity() {

    // Reference to Firebase database (more info: firebase.google.com)
    private val database = FirebaseDatabase.getInstance()

    /* onCreate runs when activity is loaded */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setUpToolbar()

        // Read values from Firebase if changes have been made in ProfileEdit
        getProfileValues()
    }

    /* Handles the toolbar setup */
    private fun setUpToolbar() {
        setSupportActionBar(findViewById(R.id.profile_toolbar))
        // Enable up/back button on toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /* Handles the creation of the toolbar */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Use toolbar described in app/src/main/res/menu/menu_profile.xml
        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    /* Handles click events from toolbar */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.edit_profile -> {
            Log.d("myTag", "Edit clicked")
            toProfileEdit()
            true
        }
        R.id.logout -> {
            Log.d("myTag", "Logout clicked")
            signOut()
            true
        }
        R.id.action_settings -> {
            Log.d("myTag", "Settings clicked")
            Toast.makeText(applicationContext, "No current settings for this app", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.help -> {
            Log.d("myTag", "Help clicked")
            Toast.makeText(applicationContext, "Haha, no help for you!", Toast.LENGTH_SHORT).show()
            true
        }
        // If we got here, the user's action was not recognized.
        else -> {
            Log.w("myTag", "User action unrecognized")
            // Invoke the superclass to handle it
            super.onOptionsItemSelected(item)
        }
    }

    /* Launches ProfileEdit activity */
    private fun toProfileEdit() {
        val intent = Intent(this, ProfileEdit::class.java)
        startActivity(intent)
        Log.d("myTag", "Switched to ProfileEdit activity")
    }

    /* Signs out user and launches SignInUp activity */
    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Log.d("myTag", "Sign-out successful")
                val intent = Intent(this, SignInUp::class.java)
                startActivity(intent)
                Log.d("myTag", "Switched to SignInUp activity")

                finish()
            }
    }

    /* Handles any changes from ProfileEdit and displays the new information */
    private fun getProfileValues() {
        // Instantiate userID from Firebase login info
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        // Location references to read from in Firebase
        val usernameRef = database.getReference("$uid/Username")
        val professionRef = database.getReference("$uid/Profession")
        val burningQRef = database.getReference("$uid/Burning Question")
        val degreeRef = database.getReference("$uid/Degree")
        val universityRef = database.getReference("$uid/University")
        val graduateYearRef = database.getReference("$uid/Graduating Year")
        val locationRef = database.getReference("$uid/Location")

        // Profile header becomes provided full name
        val fullName = user?.displayName
        val fullNameRefTV = findViewById<TextView>(R.id.ProjectNameTextView)
        fullNameRefTV.text = fullName

        // Read UserName from the database
        usernameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val refVal = dataSnapshot.getValue(String::class.java)
                Log.i("myTag", "Username value is: $refVal")
                val refTV = findViewById<TextView>(R.id.user_name)
                refTV.text = refVal
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("myTag", "Failed to read value: Username.", error.toException())
            }
        })
        // Read Profession from the database
        professionRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val refVal = dataSnapshot.getValue(String::class.java)
                Log.i("myTag", "Profession value is: $refVal")
                val refTV = findViewById<TextView>(R.id.profession)
                refTV.text = refVal
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("myTag", "Failed to read value: Profession.", error.toException())
            }
        })
        // Read Burning Question from the database
        burningQRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val refVal = dataSnapshot.getValue(String::class.java)
                Log.i("myTag", "Burning Question value is: $refVal")
                val refTV = findViewById<TextView>(R.id.burning_question)
                refTV.text = refVal
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("myTag", "Failed to read value: Burning Question.", error.toException())
            }
        })
        // Read Degree from the database
        degreeRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val refVal = dataSnapshot.getValue(String::class.java)
                Log.i("myTag", "Degree value is: $refVal")
                val refTV = findViewById<TextView>(R.id.degree)
                refTV.text = refVal
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("myTag", "Failed to read value: Degree.", error.toException())
            }
        })
        // Read University from the database
        universityRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val refVal = dataSnapshot.getValue(String::class.java)
                Log.i("myTag", "University value is: $refVal")
                val refTV = findViewById<TextView>(R.id.university)
                refTV.text = refVal
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("myTag", "Failed to read value: University.", error.toException())
            }
        })
        // Read Graduating Year from the database
        graduateYearRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val refVal = dataSnapshot.getValue(String::class.java)
                Log.i("myTag", "Graduating Year value is: $refVal")
                val refTV = findViewById<TextView>(R.id.graduating_year)
                refTV.text = refVal
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("myTag", "Failed to read value: Graduating Year.", error.toException())
            }
        })
        // Read Location from the database
        locationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val refVal = dataSnapshot.getValue(String::class.java)
                Log.i("myTag", "Location value is: $refVal")
                val refTV = findViewById<TextView>(R.id.location)
                refTV.text = refVal
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("myTag", "Failed to read value: Location.", error.toException())
            }
        })
    }
}
