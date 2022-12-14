package com.armandev.mileprix

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.armandev.mileprix.Model.UserModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class New_User : AppCompatActivity() {

    private lateinit var profileImage :ImageView
    private lateinit var auth:FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var storage : FirebaseStorage
    private lateinit var dialog: AlertDialog.Builder
    private lateinit var storeIMGuri : Uri
    private lateinit var firstName : EditText
    private lateinit var lastName : EditText
    private lateinit var btSubmit : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        supportActionBar!!.hide()


        dialog = AlertDialog.Builder(this)
            .setMessage("Update Profile...")
            .setCancelable(false)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        firstName = findViewById(R.id.enterFirstName)
        lastName = findViewById(R.id.enterLastName)

        profileImage = findViewById(R.id.profileUpload)

        val imageButton = findViewById<ImageView>(R.id.imageButton)
        imageButton.setOnClickListener{
            ImagePicker.with(this)
                .crop(1f,1f)	    			//Crop image(Optional), Check Customization for more option
                .compress(300)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(300, 300)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        btSubmit=findViewById(R.id.submitProfile)
        btSubmit.setOnClickListener {
            if(firstName.text.isEmpty()){
                Toast.makeText(this, "Please enter Your First Name", Toast.LENGTH_SHORT).show()
            }
            if (lastName.text.isEmpty()){
                Toast.makeText(this, "Please enter Your First Name", Toast.LENGTH_SHORT).show()
            }
            else if (storeIMGuri == null){
                Toast.makeText(this, "Please select profile image", Toast.LENGTH_SHORT).show()
            }else uploadData()
        }

    }

    private fun uploadData() {
        val reference = storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(storeIMGuri).addOnCompleteListener{
            if (it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener { task->
                    uploadInfo(task.toString())
                }
            }
        }
    }

    private fun uploadInfo(imgUri: String) {
        val user = UserModel(auth.uid.toString(), firstName.text.toString(),lastName.text.toString(), auth.currentUser?.phoneNumber.toString(), imgUri)

        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Data inserted successfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,OrderlistView::class.java))
                finish()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            storeIMGuri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            profileImage.setImageURI(storeIMGuri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}