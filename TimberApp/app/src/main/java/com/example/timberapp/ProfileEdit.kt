package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.support.design.widget.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileEdit : AppCompatActivity() {

    // Link to database
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        val saveChangesBtn = findViewById<Button>(R.id.save_changes_btn)
        val discardChangesBtn = findViewById<Button>(R.id.discard_changes_btn)



        saveChangesBtn.setOnClickListener {
            // clicked
            Log.i("myTag", "Save Changes clicked!")



            // write data to DB
            writeToDB()
            // (read data from database in profile) -- perhaps always read from db and have default values

            // go back to profile
            toProfile()
        }

        discardChangesBtn.setOnClickListener {
            // clicked
            Log.i("myTag", "Discard Changes clicked!")

            // DO NOT write data to DB
            // go back to profile
            toProfile()
        }
    }

    private fun writeToDB() {
        val usernameET = findViewById<TextInputEditText>(R.id.input_user_name_edit)

        // set value to write to
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val name = user?.displayName

        // profile changes
        val username = usernameET.text.toString()

        val myRef = database.getReference("$uid/Full Name")
        myRef.setValue("$name")
        val myRef2 = database.getReference("$uid/UserName")
        myRef2.setValue("$username")

        Log.i("myTag", "Username: $username")
    }

    private fun toProfile() {
        // now to profile
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        Log.i("myTag", "Switched to Profile activity")
    }
}
