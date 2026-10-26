package com.back;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.back.ai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링 부트 통합 테스트 - 전체 애플리케이션 컨텍스트를 로드
@ActiveProfiles("test") // application-test.yaml 설정 파일 사용
class BackApplicationTests {

    @Autowired
    private ChatService chatService; // 테스트할 ChatService 빈 주입

    @Test
    @DisplayName("Ollama Chat 클라이언트 테스트 - 한 번에 받기")
    void t1() {
        String response = chatService.chat("Hello, Ollama!");

        assertNotNull(response); // 응답이 null이 아닌지 확인
        assertFalse(response.isEmpty()); // 응답이 비어있지 않은지 확인
        System.out.println("Ollama Response: " + response); // 결과 출력
    }

    @Test
    @DisplayName("Ollama Chat 클라이언트 테스트 - 스트리밍으로 받기")
    void t2() {
        List<String> tokens = chatService.streamChatResponse("Hello, Ollama!")
                // Ollama에 메시지 전송, Flux<String> 반환 (스트리밍 응답)

                .collectList()
                // Flux의 모든 요소를 List로 수집 → Mono<List<String>>

                .block();
                // 비동기 → 동기 변환, 스트림이 끝날 때까지 대기

        assertNotNull(tokens);
        assertTrue(tokens.size() > 1); // 한 번에 오지 않고 여러 조각(토큰)으로 나뉘어 온다

        String response = String.join("", tokens); // 조각을 이어 붙이면 전체 답변
        assertFalse(response.isEmpty());
        System.out.println("토큰 수: " + tokens.size() + ", 첫 토큰: [" + tokens.get(0) + "]");
        System.out.println("Ollama Stream Response: " + response);
    }

    @Test
    @DisplayName("StepVerifier - 스트림이 토큰을 1개 이상 내보낸 뒤 정상 종료된다")
    void t3() {
        StepVerifier.create(chatService.streamChatResponse("Reply with one word: hello"))
                .expectNextMatches(token -> token != null) // 첫 토큰이 도착한다
                .thenConsumeWhile(token -> true)           // 나머지 토큰은 모두 소비
                .verifyComplete();                         // 에러 없이 onComplete로 끝나야 통과
    }
}
