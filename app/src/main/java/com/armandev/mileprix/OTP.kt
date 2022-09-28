package com.armandev.mileprix

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class OTP : AppCompatActivity() {
    private lateinit var btSubmit: Button
    private lateinit var enterotp: EditText
    private lateinit var setNumber: TextView
    private lateinit var resendotptv: TextView


    private lateinit var otpId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String



    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        supportActionBar!!.hide()
        enterotp = findViewById(R.id.enterOtp)
        setNumber = findViewById(R.id.myNumber)
        btSubmit = findViewById(R.id.submit)
        resendotptv = findViewById(R.id.reSendOtp)

        phoneNumber = intent.getStringExtra("mobileNo").toString()

        val x = phoneNumber
        val s:StringBuilder = java.lang.StringBuilder("")
        x.mapIndexed{ index, c ->
            if ((index+1)%4 ==0){
                s.append(" ")
            }
            s.append(c)
        }


        setNumber.text = "$s"
        auth = Firebase.auth

        sendotpTomobile()
        resendagain()
        resendtimeVisible()

        btSubmit.setOnClickListener {
                if (enterotp.text.toString().isEmpty()) Toast.makeText(
                    applicationContext,
                    "Blank Field can not be processed",
                    Toast.LENGTH_LONG
                ).show() else if (enterotp.text.toString().length != 6) Toast.makeText(
                    applicationContext, "Invalid OTP", Toast.LENGTH_LONG
                ).show() else {
                    val credential = PhoneAuthProvider.getCredential(otpId, enterotp.text.toString())
                    signInWithPhoneAuthCredential(credential)
                }
            }

    }

    private fun resendtimeVisible() {
        resendotptv.visibility = View.INVISIBLE
        resendotptv.isEnabled = false

        Handler(Looper.myLooper()!!).postDelayed({
            resendotptv.visibility = View.VISIBLE
            resendotptv.isEnabled = true
        }, 60000)
    }

    private fun resendagain() {
        resendotptv.setOnClickListener{
            sendotpTomobile()
            resendtimeVisible()
        }
    }

    private fun sendotpTomobile() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show()

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.

            // Save verification ID and resending token so we can use them later
            otpId = verificationId
            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this,Login_Successfull::class.java))
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    Toast.makeText(applicationContext,"Signin Code Error",Toast.LENGTH_LONG).show()
                    // Update UI
                }
            }
    }
}














