package com.barogo.platform.product.controller;

import com.barogo.platform.product.dto.ProductDto.ProductReq;
import com.barogo.platform.product.dto.ProductDto.InsertProductReq;
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
import org.springframework.test.web.servlet.ResultActions;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@WebAppConfiguration
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String header;

    @Before
    public void setHeader() {
        this.header = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5hQHRlc3QuY29tIiwiYXV0aCI6IltVU0VSXSIsImV4cCI6MTY3MzIzNTg5OX0.6gW5Fiih40XKgPDWXpFZHpEiTIGvIOqgJFzEmB0hCHXthSpBxUM5imJ0dIkjdXql438qeB3Sth8Bktvu1aZ9kQ";
    }

    @Test
    public void getProductListTest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", this.header);

        ProductReq productReq = new ProductReq();

        ResultActions result = this.mockMvc.perform(get("/app/products")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productReq)))
                .andExpect(status().isOk())
                .andDo(print());

        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void insertProductTest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", this.header);

        InsertProductReq insertProductReq = new InsertProductReq();
        insertProductReq.setName("뿌링클 치킨");
        insertProductReq.setAmount(12000);
        insertProductReq.setRegId("sangheon");

        ResultActions result = this.mockMvc.perform(post("/app/products")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(insertProductReq)))
                .andExpect(status().isCreated())
                .andDo(print());

        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

}
