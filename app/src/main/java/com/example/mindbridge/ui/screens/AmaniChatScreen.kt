package com.example.mindbridge.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.AmaniMessage
import com.example.mindbridge.ui.theme.*
import com.example.mindbridge.R
import kotlinx.coroutines.launch

@Composable
fun AmaniChatScreen(
    messages: List<AmaniMessage>,
    isSending: Boolean,
    onSendMessage: (String) -> Unit,
    onCrisisClick: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val quickPrompts = listOf(
        "I feel overwhelmed with exams 📚",
        "2-minute box breathing calm 🌿",
        "Agandi Amani! 🌸",
        "How do I book a private session? 🗓️",
        "I can't sleep, mind is racing 🌙",
        "Crisis helplines in Uganda 🆘"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MindBridgeBackground)
            .testTag("amani_chat_screen")
    ) {
        // Amani intro banner with custom design image
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_amani_illustration),
                    contentDescription = "Amani AI Companion",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Amani AI",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MindBridgeNavy
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(MindBridgeGreen)
                        )
                    }
                    Text(
                        text = "Instant 24/7 Support",
                        fontSize = 12.sp,
                        color = MindBridgeGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(
                    onClick = onCrisisClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CrisisRedBg),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "SOS",
                        tint = CrisisRed,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "SOS 116",
                        color = CrisisRed,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Messages list
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { message ->
                AmaniMessageBubble(message = message)
            }

            if (isSending) {
                item {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = MindBridgeBlue,
                            strokeWidth = 2.dp
                        )
                        Text(
                            text = "Amani is typing...",
                            fontSize = 13.sp,
                            color = MindBridgeTextMuted
                        )
                    }
                }
            }
        }

        // Quick prompt suggestions
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickPrompts) { prompt ->
                Surface(
                    onClick = {
                        onSendMessage(prompt)
                    },
                    shape = RoundedCornerShape(16.dp),
                    color = MindBridgeLightBlue
                ) {
                    Text(
                        text = prompt,
                        color = MindBridgeBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Chat Input Row
        Surface(
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Ask Amani anything...", fontSize = 14.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("amani_input_field"),
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MindBridgeBlue,
                        unfocusedBorderColor = MindBridgeBorder
                    )
                )

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            val text = inputText
                            inputText = ""
                            onSendMessage(text)
                            scope.launch {
                                if (messages.isNotEmpty()) {
                                    listState.animateScrollToItem(messages.size)
                                }
                            }
                        }
                    },
                    enabled = inputText.isNotBlank() && !isSending,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (inputText.isNotBlank()) MindBridgeBlue else MindBridgeLightBlue)
                        .testTag("amani_send_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = if (inputText.isNotBlank()) Color.White else MindBridgeBlue
                    )
                }
            }
        }
    }
}

@Composable
fun AmaniMessageBubble(message: AmaniMessage) {
    val isUser = message.sender == "user"

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        if (!isUser) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_amani_illustration),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp).clip(CircleShape)
                )
                Text(
                    text = "Amani AI",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MindBridgeBlue
                )
                Text(
                    text = "• ${message.time}",
                    fontSize = 11.sp,
                    color = MindBridgeTextMuted
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            color = if (isUser) MindBridgeBlue else Color.White,
            shadowElevation = if (isUser) 0.dp else 1.dp,
            modifier = Modifier.widthIn(max = 300.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                val formattedText = formatAmaniMarkdown(message.text, isUser)
                Text(
                    text = formattedText,
                    color = if (isUser) Color.White else MindBridgeNavy,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

private fun formatAmaniMarkdown(raw: String, isUser: Boolean): androidx.compose.ui.text.AnnotatedString {
    return buildAnnotatedString {
        val lines = raw.split("\n")
        lines.forEachIndexed { index, line ->
            val trimmed = line.trim()
            if (trimmed.startsWith("### ")) {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp, color = if (isUser) Color.White else MindBridgeBlue)) {
                    append(trimmed.removePrefix("### "))
                }
            } else {
                var remaining = line
                while (remaining.contains("**")) {
                    val start = remaining.indexOf("**")
                    val end = remaining.indexOf("**", start + 2)
                    if (end != -1) {
                        append(remaining.substring(0, start))
                        val boldText = remaining.substring(start + 2, end)
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(boldText)
                        }
                        remaining = remaining.substring(end + 2)
                    } else {
                        break
                    }
                }
                append(remaining)
            }
            if (index < lines.size - 1) append("\n")
        }
    }
}
