package com.example.newanimals.fragment.chat.chats_mvvm

import com.example.newanimals.db.ChatList
import com.example.newanimals.db.MessageData

class ChatListRepository {
    fun getChats(): List<ChatList>{
        return listOf(
            ChatList("kap.moral@mail.ru", "Arina", listOf(
                MessageData("kap.moral@mail.ru", "aristvodolaz@mail.ru", "Hello", "22.03.2002 12:00"),
                MessageData("kap.moral@mail.ru", "aristvodolaz@mail.ru", "Kek", "22.03.2002 12:00")
            )
            ),
            ChatList("kap.moral@mail.ru", "Test 2", listOf(
                MessageData("kap.moral@mail.ru", "aristvodolaz@mail.ru", "Hello", "22.03.2002 12:00"),
                MessageData("kap.moral@mail.ru", "aristvodolaz@mail.ru", "Kek", "22.03.2002 12:00")
            )
            )
        )
    }
}