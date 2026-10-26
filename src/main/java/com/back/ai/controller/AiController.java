package com.back.ai.controller;

import com.back.ai.dto.ChatChunk;
import com.back.ai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/ai/chat")
@RequiredArgsConstructor
public class AiController {
    private final ChatService chatService;

    // text/event-stream : 토큰 하나하나를 SSE 이벤트(data:...)로 보낸다.
    // 문자열 그대로 보내면 SSE 규격상 data: 뒤의 공백 한 칸이 사라져 단어가 붙어 보인다.
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestParam String message) {
        return chatService.streamChatResponse(message);
    }

    // 토큰을 JSON(data:{"content":" How"})으로 감싸 보내면 공백이 보존된다.
    @GetMapping(value = "/chunks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatChunk> streamChatChunks(@RequestParam String message) {
        return chatService.streamChatResponse(message)
                .map(ChatChunk::new);
    }
}
