package com.example.newanimals.presenter

import com.example.newanimals.db.ChatList
import com.example.newanimals.db.MessageData
import com.example.newanimals.network.Const
import com.example.newanimals.utils.SPHelper
import com.example.newanimals.view.ChatListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatListPresenter(private val view: ChatListView) {

    fun getChats(){
        val database: FirebaseDatabase = FirebaseDatabase.getInstance(Const.URL)
        val databaseReference: DatabaseReference = database.getReference()

        val modiferLogin = SPHelper.getLogin().replace(".", "_")
        val userLoginRef: DatabaseReference = databaseReference.child("Chats").child(modiferLogin)

        userLoginRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chats: MutableList<ChatList> = ArrayList()

                for (chatsSnapshot in snapshot.children) {
                    val login = chatsSnapshot.key ?: ""
                    val name = chatsSnapshot.child("name").getValue(String::class.java) ?: ""
                    val messages: MutableList<MessageData> = ArrayList() // Оставляем список сообщений пустым для Chats

                    // Создаем объект Chats с полученными данными
                    val chat = ChatList(login, name, messages)
                    chats.add(chat)
                }

                view.getChats(chats)
            }

            override fun onCancelled(error: DatabaseError) {
                view.errorMsg(error.message)
            }
        })
    }
}