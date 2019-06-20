package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Home : AppCompatActivity() {

    // Link to database
    private val database = FirebaseDatabase.getInstance()

    // onCreate function runs when activity is loaded
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // use a toolbar on this activity
        setSupportActionBar(findViewById(R.id.home_toolbar))
        // enable Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val signOutBtn = findViewById<Button>(R.id.signOutBtn)
        val writeDBBtn = findViewById<Button>(R.id.writeDBBtn)
        val readDBBtn = findViewById<Button>(R.id.readDBBtn)

        // Logout and go to first activity
        signOutBtn.setOnClickListener {
            // clicked
            Log.d("myTag", "logout clicked!")

            signOut()
        }

        writeDBBtn.setOnClickListener {
            // clicked
            Log.d("myTag", "writeDB clicked!")

            writeToDB()
        }

        readDBBtn.setOnClickListener {
            // clicked
            Log.d("myTag", "readDB clicked!")

            readFromDB()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.open_profile -> {
            Log.d("myTag", "Profile clicked")
            true
        }
        R.id.logout -> {
            Log.d("myTag", "Logout clicked")
            true
        }
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            Log.d("myTag", "Settings clicked")
            true
        }
        R.id.help -> {
            Log.d("myTag", "Help clicked")
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // Sign-out successful
                Log.d("myTag", "sign-out successful")

                // now back to first activity
                val intent = Intent(this, SignInUp::class.java)
                startActivity(intent)

                Log.d("myTag", "switched activities")
            }
    }

    private fun writeToDB() {
        // set value to write to
        val myRef = database.getReference("message")
        myRef.setValue("Hello, World!")
    }

    private fun readFromDB() {
        //set value to read from
        val myRef = database.getReference("message")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d("myTag", "Value is: $value")

                // show value as popup msg
                Toast.makeText(applicationContext, "Value is: $value", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("myTag", "Failed to read value.", error.toException())
            }
        })
    }
}
