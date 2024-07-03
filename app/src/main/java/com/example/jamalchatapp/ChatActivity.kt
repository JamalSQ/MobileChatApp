package com.example.jamalchatapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jamalchatapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView:RecyclerView
    private lateinit var msgbox:EditText
    private lateinit var sendbtn:ImageView
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageAdapter:MessageAdater
    private lateinit var messageList:ArrayList<Message>
    private lateinit var MyDebRef:DatabaseReference
    private lateinit var toolbar: Toolbar
    var reveiverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val name=intent.getStringExtra("name")
        val receiveruid=intent.getStringExtra("uid")
        val pImageUrl=intent.getStringExtra("pImageUrl")?.toUri()


//        setting of the toolbar at chatActivity screen
        val toolbar = findViewById<Toolbar>(R.id.chattoolbar)
        val profileImage = findViewById<ImageView>(R.id.chattoolbarImage)
        val uname = findViewById<TextView>(R.id.chatName)


        val backScreenicon = findViewById<ImageView>(R.id.chatbackScreen)
        backScreenicon.setOnClickListener({
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        })

        uname.setText(name)
        setSupportActionBar(toolbar)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        Glide.with(this)
            .load(pImageUrl)
            .placeholder(R.drawable.placeholder)
            .into(profileImage)


        val senderid=FirebaseAuth.getInstance().currentUser?.uid

        MyDebRef=FirebaseDatabase.getInstance().getReference()

        senderRoom=receiveruid + senderid
        reveiverRoom=senderid + receiveruid

        chatRecyclerView=binding.chatRecyclerView
        msgbox=binding.msgBox
        sendbtn=binding.imgview

        messageList= ArrayList()
        messageAdapter= MessageAdater(this,messageList)
        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

        MyDebRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message=postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        sendbtn.setOnClickListener {
            val message=msgbox.text.toString()
            val messageObject=Message(message,senderid)
            MyDebRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                MyDebRef.child("chats").child(reveiverRoom!!).child("messages").push().setValue(messageObject)
            }
            msgbox.setText("")
        }
    }
}