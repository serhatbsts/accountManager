package com.Project.accountManager.services;

import com.Project.accountManager.entities.Account;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.AccountRepository;
import com.Project.accountManager.request.AccountCreateRequest;
import com.Project.accountManager.request.AccountDepositRequest;
import com.Project.accountManager.request.AccountWithdrawalRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveOneAccount() {

        Long userId = 1L;
        AccountCreateRequest accountCreateRequest = new AccountCreateRequest();
        accountCreateRequest.setUserId(userId);
        accountCreateRequest.setId(1L);
        accountCreateRequest.setAccountNumber(1324);
        accountCreateRequest.setMoney(50);

        User user = new User();
        when(userService.getOneUser(userId)).thenReturn(user);

        Account accountToSave = new Account();
        accountToSave.setId(accountCreateRequest.getId());
        accountToSave.setAccountNumber(accountCreateRequest.getAccountNumber());
        accountToSave.setMoney(accountCreateRequest.getMoney());
        accountToSave.setUser(user);
        when(accountRepository.save(any(Account.class))).thenReturn(accountToSave);

        Account savedAccount = accountService.saveOneAccount(accountCreateRequest);

        assertEquals(accountCreateRequest.getId(), savedAccount.getId());
        assertEquals(accountCreateRequest.getAccountNumber(), savedAccount.getAccountNumber());
        assertEquals(accountCreateRequest.getMoney(), savedAccount.getMoney());
        assertEquals(user, savedAccount.getUser());
    }

    @Test
    public void testGetAllAccount() {
        Long userId = 1L;
        Optional<Long> optionalUserId = Optional.of(userId);

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        accounts.add(new Account());
        when(accountRepository.findByUserId(userId)).thenReturn(accounts);

        List<Account> result = accountService.getAllAccount(optionalUserId);

        assertEquals(accounts.size(), result.size());
        assertEquals(accounts.get(0).getAccountNumber(), result.get(0).getAccountNumber());
        assertEquals(accounts.get(1).getAccountNumber(), result.get(1).getAccountNumber());
    }

    @Test
    public void testGetOneAccount() {
        Long accountId = 1L;
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Account result = accountService.getOneAccount(accountId);

        assertEquals(account.getId(), result.getId());
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
        assertEquals(account.getMoney(), result.getMoney());
    }

    @Test
    public void testDepositOneAccount() {
        long accountId = 1L;
        int depositAmount = 500;
        AccountDepositRequest accountDepositRequest = new AccountDepositRequest();
        accountDepositRequest.setDepositAmount(depositAmount);

        Account mockAccount = new Account();
        mockAccount.setId(accountId);
        mockAccount.setMoney(1000);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));

        Account updatedAccount = accountService.depositOneAccount(accountId, accountDepositRequest);

        verify(accountRepository, times(1)).findById(anyLong());
        verify(accountRepository, times(1)).save(any());

        assertEquals(1500, updatedAccount.getMoney(), "Balance should be 1500 after deposit");
    }



    @Test
    public void testWithdrawalOneAccount() {
        //buradaki hatayı düzelt
        Long accountId = 1L;
        int initialMoney = 1000;
        int withdrawalAmount = 1500; // Hesaptaki miktarı aşacak miktarda para çekme
        Account account = new Account(); account.setId(accountId);
        account.setMoney(initialMoney);
        account.setAccountNumber(12345);
        account.setUser(null);
        AccountWithdrawalRequest withdrawalRequest = new AccountWithdrawalRequest();
        withdrawalRequest.setWithdrawalAmount(withdrawalAmount);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Account result = accountService.withdrawalAccount(accountId, withdrawalRequest);

        assertEquals(account.getId(), result.getId());
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
        assertEquals(0, result.getMoney()); // Para sıfırlandı

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(any());
    }


    @Test
    public void testDeleteOneAccount() {
        Long accountId = 1L;

        accountService.deleteOneAccount(accountId);

        verify(accountRepository, times(1)).deleteById(accountId);
    }
}
