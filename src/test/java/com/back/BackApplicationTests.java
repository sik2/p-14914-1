package com.back;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.back.ai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


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

}
