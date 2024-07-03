package com.example.jamalchatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jamalchatapp.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class signup : AppCompatActivity() {


    private lateinit var binding:ActivitySignupBinding
    private lateinit var edtname:EditText
    private lateinit var edtEmail:EditText
    private lateinit var edtPass:EditText
    private lateinit var signUpBtn:Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var myDbRef:DatabaseReference
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)
        supportActionBar?.hide()
        mAuth=FirebaseAuth.getInstance()

        edtname=binding.edtname
        edtEmail=binding.edtemail
        edtPass=binding.editPass
        signUpBtn=binding.signupbtn
        
        signUpBtn.setOnClickListener {
            val name=edtname.text.toString()
            val email=edtEmail.text.toString()
            val password=edtPass.text.toString()

            if(isEmailValid(email) && passValidate(password)){
                progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Registering...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                signup(name,email,password)
            }
        }
    }

    private fun signup(name: String, email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    insertintoDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intnt=Intent(this,MainActivity::class.java)
                    progressDialog.dismiss()
                    finish()
                    startActivity(intnt)
                } else {
                    // If sign in fails, display a message to the user.
                    val exception = task.exception
                    exception?.let {
                        Log.e("Firebase", "Authentication failed: ${it.message}", it)
                    }
                    Toast.makeText(
                        this,
                        "Authentication failed. Please check your credentials and try again.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun isEmailValid(email: String): Boolean {
//        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$\n"
//        var status= email.matches(emailRegex.toRegex())

        if(email==""){
            Toast.makeText(
                this,
                "Please Enter Valid Email",
                Toast.LENGTH_SHORT,
            ).show()
            return false
        }else {
           return true
        }
    }

    private fun passValidate(pass:String):Boolean{
        if(pass.length>=6){
            return true
        }else{
            Toast.makeText(
                this,
                "Password must be atleast 6 characters",
                Toast.LENGTH_SHORT,
            ).show()
            return false
        }
    }
    private fun insertintoDatabase(name:String, email:String, uid:String){

        val imgurl:String="https://www.pngfind.com/pngs/m/610-6104451_image-placeholder-png-user-profile-placeholder-image-png.png"
        myDbRef=FirebaseDatabase.getInstance().getReference()
        myDbRef.child("user").child(uid).setValue(User(name,email,uid,imgurl))
    }
}