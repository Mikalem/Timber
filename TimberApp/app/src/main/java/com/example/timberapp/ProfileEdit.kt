package com.example.timberapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class ProfileEdit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        val saveChangesBtn = findViewById<Button>(R.id.save_changes_btn)

        saveChangesBtn.setOnClickListener {
            // clicked
            Log.i("myTag", "Save Changes clicked!")

            // write data to DB
            // (read data from database in profile) -- perhaps always read from db and have default values

            // go back to profile
            toProfile()
        }
    }

    private fun toProfile() {
        // now to profile
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        Log.i("myTag", "Switched to Profile activity")
    }
}
