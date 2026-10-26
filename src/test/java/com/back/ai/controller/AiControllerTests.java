package com.back.ai.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/ai/chat - Spring MVC에서 Flux 응답을 SSE로 받는다")
    void t1() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/ai/chat")
                        .param("message", "Hello, SSE!")
                        .accept(MediaType.TEXT_EVENT_STREAM))
                .andExpect(request().asyncStarted()) // Flux를 반환하면 비동기 요청으로 처리된다
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult)) // 스트림이 끝날 때까지 기다린 뒤 결과 확인
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM))
                .andExpect(content().string(containsString("data:")));
    }
}
