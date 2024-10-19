package com.Project.accountManager.services;

import com.Project.accountManager.dto.request.AccountCreateRequest;
import com.Project.accountManager.dto.request.AccountDepositRequest;
import com.Project.accountManager.dto.request.AccountWithdrawalRequest;
import com.Project.accountManager.entities.Account;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/*
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AccountResponseServiceTest{
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;
    @InjectMocks
    private AccountService underTest;

  /*  @Test
    public void createAccountWhenUserExist(){
        var accountCreateRequest=new AccountCreateRequest();
        accountCreateRequest.setId(1L);
        accountCreateRequest.setUserId(2L);
        accountCreateRequest.setAccountNumber(1324);
        accountCreateRequest.setMoney(50);

        var user=new User();
        when(userService.getUserById(accountCreateRequest.getUserId())).thenReturn(user);
        AccountResponse actual = underTest.createAccount(accountCreateRequest);
        assertEquals(accountCreateRequest.getAccountNumber(), actual.getAccountNumber());
        assertEquals(accountCreateRequest.getMoney(), actual.getMoney());
        assertEquals(user.getName(), actual.getUserName());

    }*/
   /* @Test
    public void testGetAllAccount(){
        Long userId=1L;
        Optional<Long> optionalUserId=Optional.of(userId);

        List<Account> accounts=new ArrayList<>();
        accounts.add(new Account());
        accounts.add(new Account());
        when(accountRepository.findByUserId(userId)).thenReturn(accounts);

        List<Account> result = underTest.getAllAccount(optionalUserId);
        assertEquals(accounts.size(), result.size());
        assertEquals(accounts.get(0).getAccountNumber(), result.get(0).getAccountNumber());
        assertEquals(accounts.get(1).getAccountNumber(), result.get(1).getAccountNumber());
    }
    @Test
    public void testGetOneAccount() {
        Long accountId = 1L;
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        Account result = underTest.getOneAccount(accountId);
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
        Account updatedAccount = underTest.depositOneAccount(accountId, accountDepositRequest);

        verify(accountRepository, times(1)).findById(anyLong());
        verify(accountRepository, times(1)).save(any());

        assertEquals(1500, updatedAccount.getMoney(), "Balance should be 1500 after deposit");
    }*/
  /*  @Test
    /*public void testWithdrawalOneAccount() {
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
        Account result = underTest.withdrawalAccount(accountId, withdrawalRequest);

        assertEquals(account.getId(), result.getId());
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
        assertEquals(0, result.getMoney()); // Para sıfırlandı
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(any());
    }
    @Test
    public void testDeleteOneAccount() {
        Long accountId = 1L;
        underTest.deleteOneAccount(accountId);

        verify(accountRepository, times(1)).deleteById(accountId);
    }

    }
*/


