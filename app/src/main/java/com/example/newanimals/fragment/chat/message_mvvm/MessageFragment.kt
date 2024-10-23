package com.example.newanimals.fragment.chat.message_mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newanimals.R
import com.example.newanimals.db.MessageData
import com.example.newanimals.utils.SPHelper

class MessageFragment(val opponent: String): Fragment() {

    private lateinit var viewModel: MessageViewModel

    companion object{
        fun newInstance(opponent: String) : MessageFragment = MessageFragment(opponent)
        val TAG = MessageFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[MessageViewModel::class.java]
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    val messageList = viewModel.messages.observeAsState(emptyList())
                    val errorMessage = viewModel.error.observeAsState()
                    MessageListScreen(
                        messages = messageList.value,
                        errorMessage = errorMessage.value,
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchMessage()
    }

    @Composable
    fun MessageListScreen(messages: List<MessageData>, errorMessage: String?, viewModel: MessageViewModel) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White)
                    .wrapContentSize(Alignment.CenterStart)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = opponent,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.wrapContentSize(Alignment.Center)
                )
            }
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(messages) { message ->
                    MessageItem(message = message)
                }
            }
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }
            MessageInputField(viewModel = viewModel, opponent)
        }
    }


    @Composable
    fun MessageItem(message: MessageData) {
        Card (modifier = Modifier
            .padding(8.dp, 2.dp,8.dp,0.dp)
            .background(Color.White)
            .fillMaxWidth(),
            elevation = 6.dp){
            Column(modifier = Modifier
                .padding(8.dp)) {
                if (message.sender == SPHelper.getLogin()) {
                    Text(text = "Вы", fontSize = 12.sp, color = colorResource(R.color.orange))
                } else {
                    Text(text = opponent, fontSize = 12.sp, color = colorResource(R.color.blue))
                }
                Text(text = message.message, fontSize = 14.sp)
                Text(
                    text = message.time,
                    fontSize = 10.sp,
                    modifier = Modifier.wrapContentSize(Alignment.BottomEnd)
                )
            }
        }
    }

    @Composable
    fun MessageInputField(viewModel: MessageViewModel, opponent: String) {
        var text by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(bottom = 56.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.sendMessage(text, opponent)
                            text = ""
                        }) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send message"
                            )
                        }
                    },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Введите сообщение...") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = colorResource(R.color.orange),
                        focusedLabelColor = colorResource(R.color.orange),
                    ),
                )

            }
        }
    }


}