package com.back.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatClient chatClient;

    // 답변이 모두 만들어진 뒤 한 번에 받는다.
    public String chat(String userMessage) {
        Message message = UserMessage.builder().text(userMessage).build();
        return chatClient.prompt().messages(message).call().content();
    }
}
