package com.back.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatClient chatClient;

    // 답변이 모두 만들어진 뒤 한 번에 받는다.
    public String chat(String userMessage) {
        Message message = UserMessage.builder().text(userMessage).build();
        return chatClient.prompt().messages(message).call().content();
    }

    // 답변을 토큰 단위로 생성되는 즉시 흘려보낸다.
    public Flux<String> streamChatResponse(String userMessage) {
        Message message = UserMessage.builder().text(userMessage).build();
        return chatClient.prompt().messages(message).stream().content();
    }
}
