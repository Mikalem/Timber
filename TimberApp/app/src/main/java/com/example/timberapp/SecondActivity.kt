package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SecondActivity : AppCompatActivity() {

    // onCreate function runs when activity is loaded
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val signOutBtn = findViewById<Button>(R.id.signOutBtn)
        val writeDBBtn = findViewById<Button>(R.id.writeDBBtn)
        val readDBBtn = findViewById<Button>(R.id.readDBBtn)

        // Logout and go to first activity
        signOutBtn.setOnClickListener {
            // clicked
            Log.i("myTag", "logout clicked!")

            signOut()
        }

        writeDBBtn.setOnClickListener {
            // clicked
            Log.i("myTag", "writeDB clicked!")

            writeToDB()
        }

        readDBBtn.setOnClickListener {
            // clicked
            Log.i("myTag", "readDB clicked!")
//            readFromDB()
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

    private fun writeToDB() {
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }
    
}
