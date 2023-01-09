package com.barogo.platform.auth.controller;

import com.barogo.platform.auth.dto.LoginReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@WebAppConfiguration
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void loginTest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUserId("sangheon");
        loginReqDto.setPassword("qwerqwer12#$");

        ResultActions result = this.mockMvc.perform(get("/auth/login")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginReqDto)))
                .andExpect(status().isOk())
                .andDo(print());

        System.out.println(result.andReturn().getResponse().getContentAsString());

    }


}
