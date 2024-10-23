package com.example.newanimals.db

data class ChatList(
    val login: String?,
    val name: String?,
    val message: List<MessageData>? ) {
    constructor() : this("", "", emptyList<MessageData>())
}
