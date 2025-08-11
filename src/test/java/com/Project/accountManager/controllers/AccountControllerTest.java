package com.Project.accountManager.controllers;

import com.Project.accountManager.dto.accountRequest.AccountCreateRequest;
import com.Project.accountManager.entities.Account;
import com.Project.accountManager.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    // Tüm hesapları başarıyla döner
    @Test
    void getAllAccount_Success() {
        when(accountService.getAllAccount(Optional.empty())).thenReturn(Collections.emptyList());

        var result = accountController.getAllAccount(Optional.empty());

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(accountService, times(1)).getAllAccount(Optional.empty());
    }

    // Hesap oluşturma başarılı
    @Test
    void createAccount_Success() {
        AccountCreateRequest request = new AccountCreateRequest();
        request.setUserId(1L);

        Account account = new Account();
        account.setId(1L);

        when(accountService.createAccount(any(AccountCreateRequest.class))).thenReturn(account);

        ResponseEntity<?> response = accountController.createAccount(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
        verify(accountService, times(1)).createAccount(request);
    }

    // userId null ise 400 dönmeli
    @Test
    void createAccount_UserIdNull_ReturnsBadRequest() {
        AccountCreateRequest request = new AccountCreateRequest();

        ResponseEntity<?> response = accountController.createAccount(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User ID must not be null", response.getBody());
        verify(accountService, never()).createAccount(any());
    }

    // Tek hesap getirme başarılı
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

    // Hesap silme başarılı
    @Test
    void deleteOneAccount_Success() {
        Long accountId = 1L;

        accountController.deleteOneAccount(accountId);

        verify(accountService, times(1)).deleteOneAccount(accountId);
    }
}
