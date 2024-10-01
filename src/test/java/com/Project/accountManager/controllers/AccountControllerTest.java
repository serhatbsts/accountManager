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

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAccount_Success() {

        when(accountService.getAllAccount(Optional.empty())).thenReturn(Collections.emptyList());


        var result = accountController.getAllAccount(Optional.empty());


        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(accountService, times(1)).getAllAccount(Optional.empty());
    }

    @Test
    void createAccount_Success() {

        AccountCreateRequest request = new AccountCreateRequest();
        request.setUserId(1L);
        Account account = new Account();
        account.setId(1L);
        when(accountService.createAccount(request)).thenReturn(account);


        ResponseEntity<?> response = accountController.createAccount(request);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
        verify(accountService, times(1)).createAccount(request);
    }

    @Test
    void createAccount_UserIdNull_ReturnsBadRequest() {

        AccountCreateRequest request = new AccountCreateRequest();


        ResponseEntity<?> response = accountController.createAccount(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User ID must not be null", response.getBody());
        verify(accountService, never()).createAccount(request);
    }

    @Test
    void getOneAccount_Success() {
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        when(accountService.getOneAccount(accountId)).thenReturn(account);

        Account result = accountController.getOneAccount(accountId);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
        verify(accountService, times(1)).getOneAccount(accountId);
    }

    @Test
    void deleteOneAccount_Success() {
        Long accountId = 1L;

        accountController.deleteOneAccount(accountId);

        verify(accountService, times(1)).deleteOneAccount(accountId);
    }
}
