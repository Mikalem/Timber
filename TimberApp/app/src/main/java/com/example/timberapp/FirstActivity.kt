package com.example.timberapp

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class FirstActivity : AppCompatActivity() {

    // random value for signed-in users
    val RC_SIGN_IN = 123

    // onCreate function runs when activity is loaded
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val loginBtn = findViewById<Button>(R.id.loginBtn)

        // checks if user is already signed in
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // user is signed in
            Log.i("myTag", "user signed in")
        } else {
            // user is not signed in
            Log.i("myTag", "user not signed in")
        }

        // Login and go to main program
        loginBtn.setOnClickListener {
            // clicked,
            Log.i("myTag", "login clicked!")
            // now sign in
            signIn()
        }
    }

    /* starts the second activity */
    private fun toSecondActivity() {

        // create second activity intent, then startActivity
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)

        Log.i("myTag", "activity switched")
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
                Log.i("myTag", "login success")

                toSecondActivity()
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Log.i("myTag", "login fail")
            }
        }
    }
}
