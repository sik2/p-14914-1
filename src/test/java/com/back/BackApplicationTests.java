package com.back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest // 스프링 부트 통합 테스트 - 전체 애플리케이션 컨텍스트를 로드
@ActiveProfiles("test") // application-test.yaml 설정 파일 사용
class BackApplicationTests {

    @Test
    void contextLoads() {
    }

}
