package dev.hari.playground.modernbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hari.playground.modernbank.dto.processPayment.PaymentRequest;
import dev.hari.playground.modernbank.exception.classes.ExchangeRatesFetchException;
import dev.hari.playground.modernbank.exception.classes.InsufficientFundsException;
import dev.hari.playground.modernbank.exception.classes.InvalidAccountException;
import dev.hari.playground.modernbank.exception.classes.PaymentRequestValidationException;
import dev.hari.playground.modernbank.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PaymentService paymentService;

    private PaymentRequest mockPaymentRequest;

    @BeforeEach
    void setUp() throws PaymentRequestValidationException, InsufficientFundsException, ExchangeRatesFetchException, InvalidAccountException {
        // Initialize your mock objects here
        mockPaymentRequest = new PaymentRequest(1L, 2L, BigDecimal.valueOf(100));

        doNothing().when(paymentService).processPayment(any(PaymentRequest.class));
    }

    @Test
    void processPayment() throws Exception {
        mockMvc.perform(post("/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPaymentRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment processed successfully"));
    }
}
