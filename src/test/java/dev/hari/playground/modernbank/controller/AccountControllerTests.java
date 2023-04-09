package dev.hari.playground.modernbank.controller;

import dev.hari.playground.modernbank.dto.getBalance.GetAccountBalanceResult;
import dev.hari.playground.modernbank.dto.getDetails.GetAccountDetailsResult;
import dev.hari.playground.modernbank.dto.getStatement.GetStatementResult;
import dev.hari.playground.modernbank.exception.classes.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.modernbank.exception.classes.InvalidAccountException;
import dev.hari.playground.modernbank.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    private GetAccountBalanceResult mockGetAccountBalanceResult;
    private GetStatementResult mockGetStatementResult;
    private GetAccountDetailsResult mockGetAccountDetailsResult;

    @BeforeEach
    public void setUp() throws InvalidAccountException, ExceededMaxRequestedTransactionsException {
        // Mock responses
        mockGetAccountBalanceResult = new GetAccountBalanceResult(1L, "NOK", BigDecimal.valueOf(1000));
        mockGetStatementResult = new GetStatementResult(1L, List.of());
        mockGetAccountDetailsResult = new GetAccountDetailsResult(1L, true, "NOK", BigDecimal.valueOf(1000));

        // Mock behaviors
        when(accountService.getBalance(anyLong())).thenReturn(mockGetAccountBalanceResult);
        when(accountService.getStatement(anyLong(), anyInt())).thenReturn(mockGetStatementResult);
        when(accountService.getDetails(anyLong())).thenReturn(mockGetAccountDetailsResult);
    }

    @Test
    void getBalance() throws Exception {
        mockMvc.perform(get("/accounts/1/balance"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value(mockGetAccountBalanceResult.accountId))
                .andExpect(jsonPath("$.currency").value(mockGetAccountBalanceResult.currency))
                .andExpect(jsonPath("$.balance").value(mockGetAccountBalanceResult.balance));
    }

    @Test
    void getStatement() throws Exception {
        mockMvc.perform(get("/accounts/1/statement").param("transactionCount", "20"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value(mockGetStatementResult.accountId))
                .andExpect(jsonPath("$.transactions").isArray());
    }

    @Test
    void getAccountDetails() throws Exception {
        mockMvc.perform(get("/accounts/1/details"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value(mockGetAccountDetailsResult.accountId))
                .andExpect(jsonPath("$.isActive").value(mockGetAccountDetailsResult.isActive))
                .andExpect(jsonPath("$.balance").value(mockGetAccountDetailsResult.balance))
                .andExpect(jsonPath("$.currency").value(mockGetAccountDetailsResult.currency));
    }
}
