package com.armandev.mileprix

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class New_User : AppCompatActivity() {
    private lateinit var profileImage :ImageView
    private lateinit var firstName : EditText
    private lateinit var lastName : EditText

    companion object{
        val GALLERY_REQ_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        supportActionBar!!.hide()

        profileImage = findViewById(R.id.profileUpload)
        val imageButton = findViewById<ImageView>(R.id.imageButton)
        imageButton.setOnClickListener{
            getPhoto()
        }

    }
    fun getPhoto() {
        

        startActivityForResult(intent, GALLERY_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK) {
            profileImage.setImageURI(data?.data)
        }
    }
}