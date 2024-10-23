package com.example.newanimals.view

import com.example.newanimals.db.ChatList

interface ChatListView {
    fun getChats(chats: List<ChatList?>)
    fun errorMsg(msg: String?)
}