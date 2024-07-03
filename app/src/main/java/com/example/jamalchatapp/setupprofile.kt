package com.example.jamalchatapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.jamalchatapp.databinding.ActivitySetupprofileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class setupprofile : AppCompatActivity() {
    private lateinit var binding: ActivitySetupprofileBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mFirebaseStorage: FirebaseStorage
    private var selectedImageUri: Uri? = null
    private lateinit var myDbRef: DatabaseReference
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySetupprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // For hiding the toolbar
        supportActionBar?.hide()

        // Getting the instances
        mAuth = FirebaseAuth.getInstance()
        mFirebaseStorage = FirebaseStorage.getInstance()
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        myDbRef = mFirebaseDatabase.reference

        binding.profilePic.setOnClickListener {
            openImagePicker()
        }

        binding.profilebtn.setOnClickListener {
            if (selectedImageUri != null) {
                // Update the image
                val reference: StorageReference = mFirebaseStorage.reference.child("profiles").child(mAuth.uid!!)
                reference.putFile(selectedImageUri!!).addOnSuccessListener {
                    // Get the download URL
                    reference.downloadUrl.addOnSuccessListener { downloadUri ->
                        val imageUrl = downloadUri.toString()
                        val uid = mAuth.uid
                        val email = mAuth.currentUser?.email
                        saveUserProfile(uid, email, imageUrl)
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            }
        }
        showProfilePic(mAuth.uid)
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            // Display the selected image in ImageView
            binding.profilePic.setImageURI(selectedImageUri)
        } else {
            Toast.makeText(this, "Failed to pick image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserProfile(uid: String?,email: String?, imageUrl: String?) {

        var userName:String = binding.updateName.text.toString()

       if(userName.isEmpty()){
           Toast.makeText(this, "Please also write the name", Toast.LENGTH_SHORT).show()
       }else{
           val user = User(userName, email, uid, imageUrl)
           val userRef = myDbRef.child("user").child(uid!!)
           userRef.setValue(user)
               .addOnSuccessListener {
                   Toast.makeText(this, "User profile saved", Toast.LENGTH_SHORT).show()
               }
               .addOnFailureListener {
                   Toast.makeText(this, "Failed to save user profile", Toast.LENGTH_SHORT).show()
               }
       }
    }

    private fun showProfilePic(currentUserId: String?) {
        if (currentUserId == null) return

        val userRef = myDbRef.child("user").child(currentUserId)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    user?.profileImageUrl?.let { imageUrl ->
                        Glide.with(this@setupprofile)
                            .load(imageUrl)
                            .placeholder(R.drawable.placeholder) // Add a default placeholder image
                            .into(binding.profilePic)
                    }
                    binding.userName.setText(user?.name)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@setupprofile, "Failed to load profile picture", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
