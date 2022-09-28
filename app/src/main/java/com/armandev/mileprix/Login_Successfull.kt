package com.armandev.mileprix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Login_Successfull : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_successfull)
        supportActionBar!!.hide()
        val btContinue = findViewById<Button>(R.id.loginButton)

        btContinue.setOnClickListener {
            startActivity(Intent(this,New_User::class.java))
        }
    }
}