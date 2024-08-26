package com.Project.accountManager.controllers;

import com.Project.accountManager.dto.accountRequest.AccountCreateRequest;
import com.Project.accountManager.entities.Account;
import com.Project.accountManager.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Bu test, yeni bir hesap oluşturma işleminin başarılı olup olmadığını kontrol eder.
    @Test
    void testCreateAccount_Success() {
        AccountCreateRequest request = new AccountCreateRequest();
        request.setUserId(1L);
        request.setBalance(new BigDecimal("100.00"));

        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal("100.00"));

        when(accountService.createAccount(any(AccountCreateRequest.class))).thenReturn(account);

        ResponseEntity<?> response = accountController.createAccount(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    // Bu test, userId'nin null olduğu durumlarda hesabın oluşturulmasını engeller.
    @Test
    void testCreateAccount_MissingUserId() {
        AccountCreateRequest request = new AccountCreateRequest();

        ResponseEntity<?> response = accountController.createAccount(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User ID must not be null", response.getBody());
    }
}
