package com.Project.accountManager.controllers;

import com.Project.accountManager.dto.accountRequest.AccountCreateRequest;
import com.Project.accountManager.dto.accountRequest.AccountDepositRequest;
import com.Project.accountManager.dto.accountRequest.AccountWithdrawalRequest;
import com.Project.accountManager.entities.Account;
import com.Project.accountManager.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;

public class AccountControllerTest {

/*    @Mock
  /  private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testCreateAccount() throws Exception {
        AccountCreateRequest request = new AccountCreateRequest();
        request.setBalance(BigDecimal.valueOf(1000));
        request.setUserId(1L);

        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountService.createAccount(any(AccountCreateRequest.class))).thenReturn(account);

        mockMvc.perform(post("/user_accounts")
                        .contentType("application/json")
                        .content("{\"balance\":1000,\"userId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    public void testDepositOneAccount() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(1500));

        AccountDepositRequest depositRequest = new AccountDepositRequest();
        depositRequest.setDepositAmount(BigDecimal.valueOf(500));


        when(accountService.depositOneAccount(anyLong(), any(AccountDepositRequest.class))).thenReturn(account);

        mockMvc.perform(put("/user_accounts/deposit/1")
                        .contentType("application/json")
                        .content("{\"depositAmount\":500}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1500));
    }


    @Test
    public void testWithdrawalAccount() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(700)); // Sonuç balansı doğru olmalı

        AccountWithdrawalRequest withdrawalRequest = new AccountWithdrawalRequest();
        withdrawalRequest.setWithdrawalAmount(BigDecimal.valueOf(300));

        when(accountService.withdrawalAccount(anyLong(), any(AccountWithdrawalRequest.class))).thenReturn(account);

        mockMvc.perform(put("/user_accounts/withdrawal/1")
                        .contentType("application/json")
                        .content("{\"withdrawalAmount\":300}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(700));
    }
*/
}
