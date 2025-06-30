package com.example.local_llm.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.local_llm.model.Message
import dev.jeziellago.compose.markdowntext.MarkdownText
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment


@Composable
fun MessageBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isUser) Color.DarkGray else Color.Gray,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            if (message.isUser) {
                Text(message.text, color = Color.White)
            } else {
                MarkdownText(
                    markdown = message.text,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
