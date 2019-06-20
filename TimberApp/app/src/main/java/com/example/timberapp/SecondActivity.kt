package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SecondActivity : AppCompatActivity() {

    // Link to database
    private val database = FirebaseDatabase.getInstance()
    private val dbMessage = findViewById<EditText>(R.id.db_msg)

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
            Log.d("myTag", "logout clicked!")

            signOut()
        }

        writeDBBtn.setOnClickListener {
            // clicked
            Log.d("myTag", "writeDB clicked!")

            setVisibility(1)

//            writeToDB(getUserInput())
        }

        readDBBtn.setOnClickListener {
            // clicked
            Log.d("myTag", "readDB clicked!")

            setVisibility(0)

//            readFromDB()
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // Sign-out successful
                Log.d("myTag", "sign-out successful")

                // now back to first activity
                val intent = Intent(this, FirstActivity::class.java)
                startActivity(intent)

                Log.d("myTag", "switched activities")
            }
    }

    private fun setVisibility(value: Int) {
        when (value) {
            // make visible
            1 -> dbMessage.visibility = View.VISIBLE
            // make invisible
            0 -> dbMessage.visibility = View.INVISIBLE
            // error
            else -> Log.e("myTag", "improper use of setVisibility() function")
        }
    }

    private fun getUserInput(): String {
        val input = dbMessage.text.toString().trim()

        // input should not be empty
        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Title required", Toast.LENGTH_SHORT).show()
        }

        // input should be less than 31 characters
        if (input.length > 30) {
            Toast.makeText(applicationContext, "Title too long", Toast.LENGTH_SHORT).show()
        }

        // display entered text
        Toast.makeText(applicationContext, "'$input' entered", Toast.LENGTH_SHORT).show()

        return input
    }

    private fun writeToDB(msg: String) {
        // set value to write to
        val myRef = database.getReference("message")
        myRef.setValue(msg)
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
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("myTag", "Failed to read value.", error.toException())
            }
        })
    }
}
