package com.example.local_llm.model

data class Message(
    val id: Int,
    val text: String,
    val isUser: Boolean
)
