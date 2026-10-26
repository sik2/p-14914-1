package com.back.ai.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.context.reactive.ReactiveWebApplicationContext;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient // WebTestClient 자동 구성
class AiControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("webmvc를 제거하면 리액티브(WebFlux) 애플리케이션으로 실행된다")
    void t0() {
        assertInstanceOf(ReactiveWebApplicationContext.class, applicationContext);
    }

    @Test
    @DisplayName("GET /api/v1/ai/chat - SSE 스트리밍 테스트")
    void t1() {
        List<String> events = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/ai/chat")
                        .queryParam("message", "Hello, SSE!")
                        .build())
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .returnResult(String.class)
                .getResponseBody() // SSE 이벤트 하나가 Flux의 요소 하나
                .collectList()
                .block(); // 구독해야 실제로 요청이 실행된다

        assertNotNull(events);
        assertFalse(events.isEmpty());
        System.out.println("SSE 이벤트 수: " + events.size());
        System.out.println("SSE Response: " + String.join("", events));
    }
}
