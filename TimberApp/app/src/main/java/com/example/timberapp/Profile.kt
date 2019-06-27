package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.firebase.ui.auth.AuthUI

class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // use a toolbar on this activity
        setSupportActionBar(findViewById(R.id.profile_toolbar))
        // enable Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.edit_profile -> {
            Log.i("myTag", "Edit clicked")
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

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // Sign-out successful
                Log.d("myTag", "Sign-out successful")

                // now back to first activity
                val intent = Intent(this, SignInUp::class.java)
                startActivity(intent)

                Log.d("myTag", "Switched to SignInUp activity")

                finish()
            }
    }
}
