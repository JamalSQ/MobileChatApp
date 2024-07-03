package com.example.jamalchatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdater(val context:Context,val messageList:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val item_receive=1;
        val item_sent=2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val view=LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return receiveViewHolder(view)
        }else{
            val view=LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return sentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMsg=messageList[position]

        if(holder.javaClass==sentViewHolder::class.java){
            val view=holder as sentViewHolder
            holder.sentmessage.text=currentMsg.message
//            holder.userimg.setImageResource(currentMsg.userImage)
        }else{
            val view =holder as receiveViewHolder
            holder.receivmsg.text=currentMsg.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentmsg=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmsg.SenderId)){
            return item_sent
        }else{
            return item_receive
        }
    }

    class sentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val sentmessage=itemView.findViewById<TextView>(R.id.sendermsg)
        val userimg = itemView.findViewById<ImageView>(R.id.ivUserImage)
    }
    class receiveViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val receivmsg=itemView.findViewById<TextView>(R.id.receivermsg)
        val userimg = itemView.findViewById<ImageView>(R.id.ivUserImage)

    }

}