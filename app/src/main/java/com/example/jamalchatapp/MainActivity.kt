package com.example.jamalchatapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.TextView

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jamalchatapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var UserRecyclerView: RecyclerView
    private lateinit var userlist:ArrayList<User>
    private lateinit var adapter:UserAdapter
    private lateinit var mAuth:FirebaseAuth
    private lateinit var binding:ActivityMainBinding
    private lateinit var MyDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//bind the files
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

// firebase authentication
        mAuth=FirebaseAuth.getInstance()
        MyDbRef=FirebaseDatabase.getInstance().getReference()

        userlist=ArrayList()
        adapter=UserAdapter(this,userlist)


//Setting the data using adapter through the recyclerView into the main file
        UserRecyclerView=binding.recyclerview
        UserRecyclerView.layoutManager=LinearLayoutManager(this)
        UserRecyclerView.adapter=adapter

        MyDbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for(postsnapshot in snapshot.children){
                    val currentUser=postsnapshot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid != currentUser?.uid){
                        userlist.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        //        add the custome toolbar
        var toolbar=findViewById<Toolbar>(R.id.chattoolbar1)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menubar,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout){
            mAuth.signOut()
            val intent=Intent(this,login::class.java)
            finish()
            startActivity(intent)
            return true
        }else if(item.itemId==R.id.setting){
            val intent=Intent(this,setupprofile::class.java)
            startActivity(intent)
            return true
        }
        return true
    }
}