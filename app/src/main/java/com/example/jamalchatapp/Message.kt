package com.example.jamalchatapp

import java.net.URI

class Message {
    var message:String?=null
    var SenderId:String?=null

    constructor(){}

    constructor(messge:String?, SenderId:String?)
    {
        this.message=messge
        this.SenderId=SenderId

    }
}


//data class Message(
//    var message:String,
//    var SenderId:String,
//    val userImage : URI
//)