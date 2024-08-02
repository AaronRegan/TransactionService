package com.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.transaction.Application;
import com.transaction.service.dto.TransactionDto;
import com.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.google.common.base.Charsets;
import org.testcontainers.shaded.com.google.common.io.Resources;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *  Aaron Regan - aaronregan20@gmail.com
 */
@Slf4j
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionControllerTest {

    @Autowired
    protected WebApplicationContext context;

    @MockBean
    TransactionService transactionService;

    protected MockMvc mockMvc;

    private ObjectMapper mapper;

    private String transactionUnParseableJsonString;
    private String transactionInvalidJsonString;

    @Before
    public void setUp() throws IOException {
        mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        transactionUnParseableJsonString = Resources.toString(
                Resources.getResource("dto/transactions/transactionUnparseable.json"),
                Charsets.UTF_8);

        transactionInvalidJsonString = Resources.toString(
                Resources.getResource("dto/transactions/transactionInvalid.json"),
                Charsets.UTF_8);
    }

    @Test
    public void postValidTransaction_201() throws Exception {
        TransactionDto transactionDto = new TransactionDto(new BigDecimal("12.3343"), ZonedDateTime.now());

        mockMvc.perform(
                post("/transactions")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(transactionDto)))
                .andExpect(status().isCreated());

        verify(transactionService, times(1)).storeNewTransaction(any());
    }

    @Test
    public void postValidTransactionOlderThan60Seconds_204() throws Exception {
        given(transactionService.checkTransactionTimeOlderThanOneMinute(any())).willReturn(true);

        TransactionDto transactionDto = new TransactionDto(new BigDecimal("12.3343"), ZonedDateTime.now().minusHours(61L));

        mockMvc.perform(
                post("/transactions")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(transactionDto)))
                .andExpect(status().isNoContent());

        verify(transactionService, times(1)).checkTransactionTimeOlderThanOneMinute(any());
    }

    @Test
    public void postValidTransactionInTheFuture_422() throws Exception {
        given(transactionService.checkTransactionTimeInFuture(any())).willReturn(true);

        TransactionDto transactionDto = new TransactionDto(new BigDecimal("12.3343"), ZonedDateTime.now().plusSeconds(2L));

        mockMvc.perform(
                post("/transactions")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(transactionDto)))
                .andExpect(status().isUnprocessableEntity());

        verify(transactionService, times(1)).checkTransactionTimeInFuture(any());
    }

    @Test
    public void postInvalidTransaction_400() throws Exception {
        mockMvc.perform(
                post("/transactions")
                        .contentType("application/json")
                        .content(transactionInvalidJsonString))
                .andExpect(status().isBadRequest());

        verify(transactionService, times(0)).storeNewTransaction(any());
    }

    @Test
    public void postUnparseableTransactionJson_422() throws Exception {
        mockMvc.perform(
                post("/transactions")
                        .contentType("application/json")
                        .content(transactionUnParseableJsonString))
                .andExpect(status().isUnprocessableEntity());
    }
}
