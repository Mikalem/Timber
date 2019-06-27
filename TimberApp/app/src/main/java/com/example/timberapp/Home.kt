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

    // pressing back on your phone
    private var backPressedTime : Long = 0
    // Link to database
    private val database = FirebaseDatabase.getInstance()

    // onCreate function runs when activity is loaded
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // use a toolbar on this activity
        setSupportActionBar(findViewById(R.id.home_toolbar))

        val writeDBBtn = findViewById<Button>(R.id.writeDBBtn)
        val readDBBtn = findViewById<Button>(R.id.readDBBtn)

        writeDBBtn.setOnClickListener {
            // clicked
            Log.i("myTag", "writeDB clicked!")

            writeToDB()
        }

        readDBBtn.setOnClickListener {
            // clicked
            Log.i("myTag", "readDB clicked!")

            readFromDB()
        }
    }

    // pressing back twice logs you out
    override fun onBackPressed() {
        val backToast = Toast.makeText(applicationContext,
            "Press back again to logout",
            Toast.LENGTH_SHORT)
        Log.i("myTag", "Back key pressed")

        // pressed back twice quickly
        if (backPressedTime + 1500 > System.currentTimeMillis()) {
            Log.i("myTag", "backPressedTime interval = $backPressedTime")
            backToast.cancel()
            signOut()
            return
        }
        // show toast (first back-key press or second too slow)
        else {
            Log.i("myTag", "Back key pressed again")
            backToast.show()
        }

        // first press grabs start time to compare for second press
        backPressedTime = System.currentTimeMillis()
    }

    // set up toolbar from menu_home (menu) resource
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    // do stuff when various toolbar menu items are selected
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.open_profile -> {
            Log.i("myTag", "Profile clicked")
            toProfilePage()
            true
        }
        R.id.logout -> {
            Log.i("myTag", "Logout clicked")
            signOut()
            true
        }
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            Log.i("myTag", "Settings clicked")
            Toast.makeText(applicationContext, "No current settings for this app", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.help -> {
            Log.i("myTag", "Help clicked")
            Toast.makeText(applicationContext, "Haha, no help for you!", Toast.LENGTH_SHORT).show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            Log.w("myTag", "User action unrecognized")
            super.onOptionsItemSelected(item)
        }
    }

    private fun toProfilePage() {
        // now to profile
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        Log.i("myTag", "Switched to Profile activity")
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // Sign-out successful
                Log.i("myTag", "Sign-out successful")

                // now back to SignInUp activity
                val intent = Intent(this, SignInUp::class.java)
                startActivity(intent)

                Log.i("myTag", "Switched to SignInUp activity")

                finish()
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
                Log.i("myTag", "Value is: $value")

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
