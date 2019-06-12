package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.firebase.ui.auth.AuthUI

class SecondActivity : AppCompatActivity() {

    // onCreate function runs when activity is loaded
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val signOutBtn = findViewById<Button>(R.id.signOutBtn)

        // Logout and go to first activity
        signOutBtn.setOnClickListener {
            // clicked
            Log.i("myTag", "logout clicked!")

            signOut()
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // Sign-out successful
                Log.i("myTag", "sign-out successful")

                // now back to first activity
                val intent = Intent(this, FirstActivity::class.java)
                startActivity(intent)

                Log.i("myTag", "switched activities")
            }
    }
}
