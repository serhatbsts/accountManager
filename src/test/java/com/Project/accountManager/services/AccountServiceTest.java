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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
    void init() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setBalance(new BigDecimal("100.00"));
        testAccount.setUser(testUser);
    }

    // createAccount: başarılı oluşturma
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
        assertEquals(testUser, createdAccount.getUser());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    // getOneAccount: mevcut hesap döner
    @Test
    void getOneAccount_existingAccount_shouldReturnAccount() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        Account foundAccount = accountService.getOneAccount(1L);

        assertNotNull(foundAccount);
        assertEquals(testAccount.getId(), foundAccount.getId());
        verify(accountRepository, times(1)).findById(1L);
    }

    // getOneAccount: olmayan hesap -> null
    @Test
    void getOneAccount_nonExistingAccount_shouldReturnNull() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Account foundAccount = accountService.getOneAccount(1L);

        assertNull(foundAccount);
        verify(accountRepository, times(1)).findById(1L);
    }

    // depositOneAccount: mevcut hesap -> bakiye artar
    @Test
    void depositOneAccount_existingAccount_shouldUpdateBalance() {
        AccountDepositRequest depositRequest = new AccountDepositRequest();
        depositRequest.setDepositAmount(new BigDecimal("50.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        Account updatedAccount = accountService.depositOneAccount(1L, depositRequest);

        assertNotNull(updatedAccount);
        assertEquals(new BigDecimal("150.00"), updatedAccount.getBalance());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    // depositOneAccount: olmayan hesap -> null
    @Test
    void depositOneAccount_nonExistingAccount_shouldReturnNull() {
        AccountDepositRequest depositRequest = new AccountDepositRequest();
        depositRequest.setDepositAmount(new BigDecimal("50.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Account updatedAccount = accountService.depositOneAccount(1L, depositRequest);

        assertNull(updatedAccount);
        verify(accountRepository, never()).save(any(Account.class));
    }

    // withdrawalAccount: yeterli bakiye -> bakiye düşer
    @Test
    void withdrawalAccount_sufficientFunds_shouldUpdateBalance() {
        AccountWithdrawalRequest withdrawalRequest = new AccountWithdrawalRequest();
        withdrawalRequest.setWithdrawalAmount(new BigDecimal("30.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        Account updatedAccount = accountService.withdrawalAccount(1L, withdrawalRequest);

        assertNotNull(updatedAccount);
        assertEquals(new BigDecimal("70.00"), updatedAccount.getBalance());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    // withdrawalAccount: yetersiz bakiye -> InsufficientFundsException
    @Test
    void withdrawalAccount_insufficientFunds_shouldThrowException() {
        AccountWithdrawalRequest withdrawalRequest = new AccountWithdrawalRequest();
        withdrawalRequest.setWithdrawalAmount(new BigDecimal("200.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        InsufficientFundsException ex = assertThrows(
                InsufficientFundsException.class,
                () -> accountService.withdrawalAccount(1L, withdrawalRequest)
        );
        assertEquals("There is not enough balance in the account", ex.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    // withdrawalAccount: hesap yok -> CustomNotFoundException
    @Test
    void withdrawalAccount_nonExistingAccount_shouldThrowException() {
        AccountWithdrawalRequest withdrawalRequest = new AccountWithdrawalRequest();
        withdrawalRequest.setWithdrawalAmount(new BigDecimal("30.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        CustomNotFoundException ex = assertThrows(
                CustomNotFoundException.class,
                () -> accountService.withdrawalAccount(1L, withdrawalRequest)
        );
        assertEquals("Account not found", ex.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    // deleteOneAccount: repository çağrılır
    @Test
    void deleteOneAccount_shouldDeleteAccount() {
        accountService.deleteOneAccount(1L);

        verify(accountRepository, times(1)).deleteById(1L);
    }
}
