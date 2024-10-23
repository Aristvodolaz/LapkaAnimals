package com.example.newanimals.fragment.chat.chats_mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newanimals.db.ChatList

class ChatListViewModel: ViewModel() {

    private val chatListRepository = ChatListRepository()

    private val _chats = MutableLiveData<List<ChatList>>()
    val chats: LiveData<List<ChatList>> get() = _chats

    private val _errorMessage = MutableLiveData<String?>()
    val  errorMessage: LiveData<String?> get() = _errorMessage


    fun fetchChats(){
        try {
            _chats.value = chatListRepository.getChats()
        }catch (e:Exception){
            _errorMessage.value = "Ошибка соединения"
        }
    }

    fun  clearError(){
        _errorMessage.value = null
    }

}