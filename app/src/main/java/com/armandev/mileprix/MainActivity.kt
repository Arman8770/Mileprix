package com.armandev.mileprix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()

        findViewById<Button>(R.id.logOut).setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this,Login::class.java))
            finish()
        }
    }
}