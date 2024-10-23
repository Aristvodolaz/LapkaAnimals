package com.example.newanimals.fragment.chat.message_mvvm

import com.example.newanimals.db.MessageData

class MessageRepository {

    fun getMessage(): List<MessageData>{

        return listOf(
            MessageData("kap.moral@mail.ru", "aristvodolaz@mail.ru", "Hello", "12:34 12.04.2024"),
            MessageData("kap.moral@mail.ru", "aristvodolaz@mail.ru", "What are u doing?", "12:35 12.04.2024"),
            MessageData("aristvodolaz@mail.ru", "kap.moral@mail.ru", "Hello", "12:36 12.04.2024"),
            MessageData("aristvodolaz@mail.ru", "kap.moral@mail.ru", "Everthing is fine", "12:36 12.04.2024")
        )
    }
}