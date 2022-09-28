package com.armandev.mileprix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.hbb20.CountryCodePicker

class Login : AppCompatActivity() {

    private lateinit var pNumber : EditText
    private lateinit var sendotp : Button
    private lateinit var ccp: CountryCodePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()
        pNumber = findViewById(R.id.phoneN)
        sendotp = findViewById(R.id.sendOtp)
        ccp = findViewById(R.id.ccp)
        ccp.registerCarrierNumberEditText(pNumber)

        sendotp.setOnClickListener {
            val intent = Intent(this,OTP::class.java)
            intent.putExtra("mobileNo", ccp.fullNumberWithPlus.trim())
            startActivity(intent)

        }
    }
}
