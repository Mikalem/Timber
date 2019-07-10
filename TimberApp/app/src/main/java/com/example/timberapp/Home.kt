package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.firebase.ui.auth.AuthUI

// Time interval between back-key presses
private const val BACK_KEY_TIME_INTERVAL = 1500 // in milliseconds

/* Handles all functionality related to the Home activity */
class Home : AppCompatActivity() {

    // The time at which user presses back on phone
    private var backPressedTimeStamp : Long = 0

    /* onCreate runs when activity is loaded */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpToolbar()
    }

    /* Handles the toolbar setup */
    private fun setUpToolbar() {
        setSupportActionBar(findViewById(R.id.home_toolbar))
    }

    /* Handles the creation of the toolbar */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Use toolbar described in app/src/main/res/menu/menu_home.xml
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    /* Handles click events from toolbar */
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
            Log.i("myTag", "Settings clicked")
            Toast.makeText(applicationContext, "No current settings for this app", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.help -> {
            Log.i("myTag", "Help clicked")
            Toast.makeText(applicationContext, "Haha, no help for you!", Toast.LENGTH_SHORT).show()
            true
        }
        // If we got here, the user's action was not recognized.
        else -> {
            // Invoke the superclass to handle it.
            Log.w("myTag", "User action unrecognized")
            super.onOptionsItemSelected(item)
        }
    }

    /* Handles what happens when user presses back key */
    override fun onBackPressed() {
        val backToast = Toast.makeText(applicationContext,
            "Press back again to logout",
            Toast.LENGTH_SHORT)
        Log.i("myTag", "Back key pressed")

        // Pressed back twice within the BACK_KEY_TIME_INTERVAL specification
        if (backPressedTimeStamp + BACK_KEY_TIME_INTERVAL > System.currentTimeMillis()) {
            Log.i("myTag", "backPressedTime interval = $backPressedTimeStamp")
            backToast.cancel()
            signOut()
            return
        }
        // First back-key pressed or second+ too slow
        else {
            Log.i("myTag", "Back key pressed again")
            backToast.show()
        }

        // First press grabs start time to compare for second press
        backPressedTimeStamp = System.currentTimeMillis()
    }

    // Launch Profile page
    private fun toProfilePage() {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        Log.i("myTag", "Switched to Profile activity")
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
}
