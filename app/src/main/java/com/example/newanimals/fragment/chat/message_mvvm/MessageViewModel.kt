package com.example.newanimals.fragment.chat.message_mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newanimals.db.MessageData
import com.example.newanimals.utils.DateUtils
import com.example.newanimals.utils.SPHelper

class MessageViewModel : ViewModel(){
    private val messageRepository =  MessageRepository()

    private val _messages = MutableLiveData<List<MessageData>>()
    val messages: LiveData<List<MessageData>> get() = _messages

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchMessage(){
        try {
            _messages.value = messageRepository.getMessage()
        }catch (e: Exception){
            _error.value = "Ошибка получения данных"
        }
    }
    fun sendMessage(message: String, opponent: String) {
        if (message.isBlank()) {
            _error.value = "Message cannot be empty"
            return
        }
        val formattedTime = DateUtils.formatTimestamp(System.currentTimeMillis())

        val newMessage = MessageData(
            sender = SPHelper.getLogin(),
            receiver = opponent,
            message = message,
            time = formattedTime
        )
        _messages.value = _messages.value?.plus(newMessage) ?: listOf(newMessage)
    }
    fun clearError(){
        _error.value = null
    }
}