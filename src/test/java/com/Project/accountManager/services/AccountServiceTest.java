package com.Project.accountManager.services;

import com.Project.accountManager.dto.accountRequest.AccountCreateRequest;
import com.Project.accountManager.dto.accountRequest.AccountDepositRequest;
import com.Project.accountManager.dto.accountRequest.AccountWithdrawalRequest;
import com.Project.accountManager.entities.Account;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.exception.CustomNotFoundException;
import com.Project.accountManager.exception.InsufficientFundsException;
import com.Project.accountManager.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountService accountService;

    private User testUser;
    private Account testAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setBalance(new BigDecimal("100.00"));
        testAccount.setUser(testUser);
    }

    @Test
    void createAccount_shouldReturnCreatedAccount() {
        AccountCreateRequest request = new AccountCreateRequest();
        request.setUserId(1L);
        request.setBalance(new BigDecimal("100.00"));

        when(userService.getUserById(1L)).thenReturn(Optional.of(testUser));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);


        Account createdAccount = accountService.createAccount(request);

        assertNotNull(createdAccount);
        assertEquals(testAccount.getBalance(), createdAccount.getBalance());
        verify(accountRepository, times(1)).save(any(Account.class));
    }


    @Test
    void getOneAccount_existingAccount_shouldReturnAccount() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        Account foundAccount = accountService.getOneAccount(1L);

        assertNotNull(foundAccount);
        assertEquals(testAccount.getId(), foundAccount.getId());
    }

    @Test
    void getOneAccount_nonExistingAccount_shouldReturnNull() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Account foundAccount = accountService.getOneAccount(1L);

        assertNull(foundAccount);
    }

    @Test
    void depositOneAccount_existingAccount_shouldUpdateBalance() {
        AccountDepositRequest depositRequest = new AccountDepositRequest();
        depositRequest.setDepositAmount(new BigDecimal("50.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        Account updatedAccount = accountService.depositOneAccount(1L, depositRequest);

        assertNotNull(updatedAccount);
        assertEquals(new BigDecimal("150.00"), updatedAccount.getBalance());
    }

    @Test
    void depositOneAccount_nonExistingAccount_shouldReturnNull() {
        AccountDepositRequest depositRequest = new AccountDepositRequest();
        depositRequest.setDepositAmount(new BigDecimal("50.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Account updatedAccount = accountService.depositOneAccount(1L, depositRequest);

        assertNull(updatedAccount);
    }

    @Test
    void withdrawalAccount_sufficientFunds_shouldUpdateBalance() {
        AccountWithdrawalRequest withdrawalRequest = new AccountWithdrawalRequest();
        withdrawalRequest.setWithdrawalAmount(new BigDecimal("30.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        Account updatedAccount = accountService.withdrawalAccount(1L, withdrawalRequest);

        assertNotNull(updatedAccount);
        assertEquals(new BigDecimal("70.00"), updatedAccount.getBalance());
    }

    @Test
    void withdrawalAccount_insufficientFunds_shouldThrowException() {
        AccountWithdrawalRequest withdrawalRequest = new AccountWithdrawalRequest();
        withdrawalRequest.setWithdrawalAmount(new BigDecimal("200.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            accountService.withdrawalAccount(1L, withdrawalRequest);
        });
        assertEquals("There is not enough balance in the account", exception.getMessage());
    }

    @Test
    void withdrawalAccount_nonExistingAccount_shouldThrowException() {
        AccountWithdrawalRequest withdrawalRequest = new AccountWithdrawalRequest();
        withdrawalRequest.setWithdrawalAmount(new BigDecimal("30.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomNotFoundException.class, () -> {
            accountService.withdrawalAccount(1L, withdrawalRequest);
        });
        assertEquals("Account not found", exception.getMessage());
    }

    @Test
    void deleteOneAccount_shouldDeleteAccount() {
        accountService.deleteOneAccount(1L);

        verify(accountRepository, times(1)).deleteById(1L);
    }
}
