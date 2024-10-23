package com.example.newanimals.db

data class MessageData(val sender: String = "",
                       val receiver: String = "" ,
                       val message:String = "",
                       val time: String = "") {
    constructor() : this("", "", "", "")
}
