package com.example.local_llm.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.local_llm.model.Message
import com.example.local_llm.network.ChatApi
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    val messages = mutableStateListOf<Message>()
    val isLoading = mutableStateOf(false)
    private var messageId = 0
    private var assistantMessageText = ""

    @SuppressLint("StaticFieldLeak")
    private lateinit var appContext: Context

    fun initializeContext(context: Context) {
        this.appContext = context.applicationContext
    }

    fun setModelUri(uri: Uri) {
        ChatApi.setModelUri(uri, appContext)
        // Optional: reset conversation
        messageId = 0
        messages.clear()
    }

    fun sendMessage(prompt: String) {
        val trimmedPrompt = prompt.trim()
        if (trimmedPrompt.isBlank()) return

        messages.add(Message(messageId++, trimmedPrompt, isUser = true))
        isLoading.value = true

        val currentAssistantId = messageId++
        assistantMessageText = ""
        messages.add(Message(currentAssistantId, assistantMessageText, isUser = false))

        viewModelScope.launch {
            ChatApi.streamPromptResponse(appContext, trimmedPrompt) { chunk ->
                viewModelScope.launch {
                    assistantMessageText += chunk
                    val index = messages.indexOfFirst { it.id == currentAssistantId }
                    if (index != -1) {
                        messages[index] = messages[index].copy(text = assistantMessageText)
                    }
                    isLoading.value = false
                }
            }
        }
    }
}
