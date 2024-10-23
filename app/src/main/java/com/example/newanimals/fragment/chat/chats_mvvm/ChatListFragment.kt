package com.example.newanimals.fragment.chat.chats_mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.newanimals.db.ChatList
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.activity.WelcomeActivity
import com.example.newanimals.fragment.chat.message_mvvm.MessageFragment

class ChatListFragment: Fragment(){

    private lateinit var viewModel: ChatListViewModel

    companion object{
        fun newInstance() : ChatListFragment = ChatListFragment()
        val TAG = ChatListFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ChatListViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    val chatLists = viewModel.chats.observeAsState(emptyList())
                    val errorMessage = viewModel.errorMessage.observeAsState()
                    ChatListScreen(chats = chatLists.value, errorMessage = errorMessage.value)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchChats()
    }

    @Composable
    fun ChatItem(chat: ChatList, lastMsg: String, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .padding(8.dp, 4.dp)
                .fillMaxWidth()
                .clickable(onClick = onClick),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = chat.name ?: "Unknown", style = MaterialTheme.typography.h6)
                Text(text = chat.login ?: "No Login", style = MaterialTheme.typography.body2)
                Text(text = lastMsg, style = MaterialTheme.typography.body1)
            }
        }
    }


    @Composable
    fun ChatListScreen(chats: List<ChatList>, errorMessage: String?) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White)
                    .wrapContentSize(Alignment.CenterStart)
                    .padding(16.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = "Сообщения",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                )
            }
            LazyColumn {
                items(chats) { chat ->
                    chat.message?.get(0)?.let { ChatItem(chat, it.message){
                        (activity as WelcomeActivity).replaceFragment(chat.login?.let { it1 ->
                            MessageFragment.newInstance(
                                it1
                            )
                        }, true)
                    } }
                }
            }
            errorMessage?.let {
                ErrorSnackbar(errorMessage = it)
                viewModel.clearError()
            }
        }
    }


    @Composable
    fun ErrorSnackbar(errorMessage: String) {
        Snackbar {
            Text(text = errorMessage)
        }
    }


}