package com.barogo.platform.user.controller;

import com.barogo.platform.user.dto.UserDto.InsertUserReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String header;

    @Before
    public void setHeader() {
        this.header = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5hQHRlc3QuY29tIiwiYXV0aCI6IltVU0VSXSIsImV4cCI6MTY3MzE0Mzc4OH0.Ep3OyNALOW4Kdy7tpscbx2HnFrA1w9vQ7WGrT-CrIc1CL_o-kQQOml80tMD2N0Z_wwP8_AuFfTH9iKD6E0Gu-Q";
    }


    @Test
    public void insertUserTest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", this.header);

        InsertUserReq insertUserReq = new InsertUserReq();
        insertUserReq.setUserId("guest");
        insertUserReq.setUserName("손님");
        insertUserReq.setUserPwd("qwerqwer1234");
        insertUserReq.setEmail("guest@test.com");
        insertUserReq.setPhoneNo("01022231213");
        insertUserReq.setRegId("guest");

        this.mockMvc.perform(post("/users/register")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(insertUserReq)))
                .andExpect(status().isCreated())
                .andDo(print());

    }
}
