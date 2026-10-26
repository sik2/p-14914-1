package com.back.global.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AiConfig {

    // default 프로필에는 OpenAI, Ollama ChatModel이 모두 존재한다.
    // 파라미터 이름(openAiChatModel)과 같은 이름의 빈이 주입된다.
    @Bean
    @Profile("default")
    public ChatClient openaiChatClient(ChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel).build();
    }

    @Bean
    @Profile("test")
    public ChatClient ollamaChatClient(ChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel).build();
    }
}
