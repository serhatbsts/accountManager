package com.Project.accountManager.services;

import com.Project.accountManager.dto.accountRequest.AccountCreateRequest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Bu test, yeni bir hesap oluşturma işleminin doğru şekilde çalıştığını doğrular.
    @Test
    void testCreateAccount_Success() {
        AccountCreateRequest request = new AccountCreateRequest();
        request.setUserId(1L);
        request.setBalance(new BigDecimal("100.00"));

        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal("100.00"));
        account.setUser(user);

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.createAccount(request);

        assertEquals(1L, createdAccount.getId());
        assertEquals(new BigDecimal("100.00"), createdAccount.getBalance());
        assertEquals(user, createdAccount.getUser());
    }

    // Bu test, kullanıcı bulunamadığında createAccount metodunun nasıl davrandığını kontrol eder.
    @Test
    void testCreateAccount_UserNotFound() {
        AccountCreateRequest request = new AccountCreateRequest();
        request.setUserId(1L);
        request.setBalance(new BigDecimal("100.00"));

        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        // Method call inside try-catch or assertThrows can be added
    }
}
