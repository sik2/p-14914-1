package com.back.ai.dto;

// SSE로 보낼 토큰 한 조각. JSON으로 감싸면 토큰 앞뒤의 공백이 그대로 전달된다.
public record ChatChunk(String content) {
}
