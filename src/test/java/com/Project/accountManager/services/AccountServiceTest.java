package com.Project.accountManager.services;

import com.Project.accountManager.dto.request.AccountCreateRequest;
import com.Project.accountManager.dto.request.AccountDepositRequest;
import com.Project.accountManager.dto.request.AccountWithdrawalRequest;
import com.Project.accountManager.entities.Account;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount_Success() {
        User user = new User();
        user.setId(1L);

        AccountCreateRequest request = new AccountCreateRequest();
        request.setBalance(BigDecimal.valueOf(1000));
        request.setUserId(1L);

        when(userService.getUserById(1L)).thenReturn(user);

        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));
        account.setUser(user);

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.createAccount(request);

        assertNotNull(createdAccount);
        assertEquals(BigDecimal.valueOf(1000), createdAccount.getBalance());
        assertEquals(user, createdAccount.getUser());
    }

    @Test
    public void testDepositOneAccount_Success() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(1000));

        AccountDepositRequest depositRequest = new AccountDepositRequest();
        depositRequest.setDepositAmount(BigDecimal.valueOf(500));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.depositOneAccount(1L, depositRequest);

        assertNotNull(updatedAccount);
        assertEquals(BigDecimal.valueOf(1500), updatedAccount.getBalance());
    }

    @Test
    public void testWithdrawalAccount_Success() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(1000));

        AccountWithdrawalRequest withdrawalRequest = new AccountWithdrawalRequest();
        withdrawalRequest.setWithdrawalAmount(BigDecimal.valueOf(300));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.withdrawalAccount(1L, withdrawalRequest);

        assertNotNull(updatedAccount);
        assertEquals(BigDecimal.valueOf(700), updatedAccount.getBalance());
    }

    @Test
    public void testGetOneAccount_NotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Account account = accountService.getOneAccount(1L);

        assertNull(account);
    }
}
