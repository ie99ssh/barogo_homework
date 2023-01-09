package com.barogo.platform.delivery.controller;

import com.barogo.platform.delivery.dto.DeliveryDto.DeliveryReq;
import com.barogo.platform.delivery.dto.DeliveryDto.InsertDeliveryReq;
import com.barogo.platform.delivery.dto.DeliveryDto.UpdateDeliveryAddressReq;
import com.barogo.platform.delivery.dto.DeliveryDto.UpdateDeliveryStateReq;
import com.barogo.platform.delivery.dto.DeliveryProductDto.InsertDeliveryProductReq;
import com.barogo.platform.delivery.entity.Delivery;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@WebAppConfiguration
public class DeliveryControllerTest {

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
    public void getDeliveryListTest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", this.header);

        DeliveryReq deliveryReq = new DeliveryReq();
        deliveryReq.setFromDate("2023-01-07");
        deliveryReq.setToDate("2023-01-09");

        ResultActions result = this.mockMvc.perform(get("/app/deliveries")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliveryReq)))
                .andExpect(status().isOk())
                .andDo(print());

        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void insertDeliveryTest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", this.header);

        InsertDeliveryReq insertDeliveryReq = new InsertDeliveryReq();
        insertDeliveryReq.setDestAddress("강서구 내발산동");
        insertDeliveryReq.setRegId("sangheon");

        List<InsertDeliveryProductReq> productList = new ArrayList<>();
        InsertDeliveryProductReq product1 = new InsertDeliveryProductReq();
        product1.setCount(1);
        product1.setProductSeq(4L);

        InsertDeliveryProductReq product2 = new InsertDeliveryProductReq();
        product2.setCount(2);
        product2.setProductSeq(5L);

        productList.add(product1);
        productList.add(product2);

        insertDeliveryReq.setProductList(productList);

        ResultActions result = this.mockMvc.perform(post("/app/deliveries")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(insertDeliveryReq)))
                .andExpect(status().isCreated())
                .andDo(print());

        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void updateDeliveryAddressTest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", this.header);

        UpdateDeliveryAddressReq updateDeliveryAddressReq = new UpdateDeliveryAddressReq();
        updateDeliveryAddressReq.setSeq(5L);
        updateDeliveryAddressReq.setDestAddress("강서구 내발산동 다른곳");
        updateDeliveryAddressReq.setUpdId("sangheon");

        ResultActions result = this.mockMvc.perform(put("/app/deliveries/address")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDeliveryAddressReq)))
                .andExpect(status().isOk())
                .andDo(print());

        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void updateDeliveryStateTest() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=UTF-8");
        headers.set("Authorization", this.header);

        UpdateDeliveryStateReq updateDeliveryStateReq = new UpdateDeliveryStateReq();
        updateDeliveryStateReq.setSeq(5L);
        updateDeliveryStateReq.setState(Delivery.State.COMPLETE);
        updateDeliveryStateReq.setUpdId("sangheon");

        ResultActions result = this.mockMvc.perform(put("/app/deliveries/state")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDeliveryStateReq)))
                .andExpect(status().isOk())
                .andDo(print());

        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

}
