package com.example.timberapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// Time interval between back-key presses
private const val BACK_KEY_TIME_INTERVAL = 1500 // in milliseconds
// Random value for signed-in users
private const val RC_SIGN_IN = 123

/* Handles all functionality related to the SignInUp activity */
class SignInUp : AppCompatActivity() {

    // Reference to Firebase database (more info: firebase.google.com)
    private val database = FirebaseDatabase.getInstance()
    // Initialize back-key pressed timeStamp
    private var backPressedTimeStamp : Long = 0

    /* onCreate runs when activity is loaded */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_up)

        userStatus()

        setUpClickEvents()
    }

    /* Determines if the user is already signed in */
    private fun userStatus() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in, launch Home activity
            Log.i("myTag", "User signed in")
            toHomeActivity()
        } else {
            // User is not signed in
            Log.i("myTag", "User not signed in")
        }
    }

    /* Sets up and handles LoginBtn click event */
    private fun setUpClickEvents() {
        val loginBtn = findViewById<Button>(R.id.loginBtn)

        // Sign-in the user when clicked
        loginBtn.setOnClickListener {
            Log.d("myTag", "Login clicked!")
            signIn()
        }
    }

    /* Handles usage of the back-key */
    override fun onBackPressed() {
        val backToast = Toast.makeText(applicationContext,
            "Press back again to exit the app",
            Toast.LENGTH_SHORT)
        Log.d("myTag", "Back key pressed")

        // Pressed back twice within the BACK_KEY_TIME_INTERVAL specification
        if (backPressedTimeStamp + BACK_KEY_TIME_INTERVAL > System.currentTimeMillis()) {
            Log.d("myTag", "backPressedTimeStamp = $backPressedTimeStamp")
            backToast.cancel()
            super.onBackPressed()
            return
        }
        // First back-key pressed or second+ too slow
        else {
            Log.d("myTag", "Back key pressed again")
            backToast.show()
        }

        // first press grabs start time to compare for second press
        backPressedTimeStamp = System.currentTimeMillis()
    }

    /* Launches Home activity */
    private fun toHomeActivity() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        Log.d("myTag", "Switched to Home activity")

        finish()
    }

    /* Signs in user using the Firebase UI */
    private fun signIn() {
        // Choose authentication providers:
        // email, phone, google, facebook, twitter
        val providers = arrayListOf(
            // only email chosen
            AuthUI.IdpConfig.EmailBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }

    /* When sign-in is complete, will receive the result here */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                Log.i("myTag", "Login success")
                val user = FirebaseAuth.getInstance().currentUser
                val uid = user?.uid
                val fullName = user?.displayName

                if (fullName != null) {
                    val fullNameRef = database.getReference("$uid/Full Name")
                    fullNameRef.setValue(fullName)
                    Log.d("myTag", "Full Name: $fullName")
                }

                // show a welcome popup msg
                Toast.makeText(applicationContext, "Welcome $fullName!", Toast.LENGTH_SHORT).show()

                toHomeActivity()
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Log.e("myTag", "Login fail")
            }
        }
    }
}
