package com.example.jamalchatapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jamalchatapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnsignUp:Button
    private lateinit var mAuth:FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        mAuth=FirebaseAuth.getInstance()


        edtEmail=binding.edtemail
        edtPassword=binding.edtpassword
        btnLogin=binding.loginbtn
        btnsignUp=binding.signupbtn


        btnsignUp.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val email=edtEmail.text.toString()
            val password= edtPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please fill all field", Toast.LENGTH_SHORT).show()
            }else{
                progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Checking Credentials...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                login(email,password)

            }

        }

    }

    private fun login(email:String,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intnt=Intent(this,MainActivity::class.java)
                    finish()
                    progressDialog.dismiss()
                    startActivity(intnt)
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this,"Wrong Credentials",Toast.LENGTH_SHORT).show()
                }
            }

    }
}