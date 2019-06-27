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

class SignInUp : AppCompatActivity() {

    // pressing back on your phone
    private var backPressedTime : Long = 0
    // random value for signed-in users
    val RC_SIGN_IN = 123

    // onCreate function runs when activity is loaded
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_up)

        val loginBtn = findViewById<Button>(R.id.loginBtn)

        // checks if user is already signed in
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // user is signed in
            Log.i("myTag", "User signed in")
            toHomeActivity()
        } else {
            // user is not signed in
            Log.i("myTag", "User not signed in")
        }

        // Login and go to main program
        loginBtn.setOnClickListener {
            // clicked,
            Log.i("myTag", "Login clicked!")
            // now sign in
            signIn()
        }
    }

    // pressing back twice exits the app
    override fun onBackPressed() {
        val backToast = Toast.makeText(applicationContext,
            "Press back again to exit the app",
            Toast.LENGTH_SHORT)
        Log.i("myTag", "Back key pressed")

        // pressed back twice quickly
        if (backPressedTime + 1500 > System.currentTimeMillis()) {
            Log.i("myTag", "backPressedTime interval = $backPressedTime")
            backToast.cancel()
            super.onBackPressed()
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

    /* starts the second activity */
    private fun toHomeActivity() {

        // create second activity intent, then startActivity
        val intent = Intent(this, Home::class.java)
        startActivity(intent)

        Log.i("myTag", "Switched to Home activity")

        finish()
    }

    /* sign-in the user using the firebase UI */
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

    /* When signIn is complete, will receive the result in onActivityResult */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                Log.i("myTag", "Login success")
                val user = FirebaseAuth.getInstance().currentUser

                // show a welcome popup msg
                Toast.makeText(applicationContext, "Welcome $user!", Toast.LENGTH_SHORT).show()

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
