package com.Project.accountManager.controllers;

import com.Project.accountManager.dto.accountRequest.AccountCreateRequest;
import com.Project.accountManager.entities.Account;
import com.Project.accountManager.dto.accountRequest.AccountDepositRequest;
import com.Project.accountManager.dto.accountRequest.AccountWithdrawalRequest;
import com.Project.accountManager.services.AccountService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user_accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController( AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccount(@RequestParam Optional<Long> userId) {
        return accountService.getAllAccount(userId);
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountCreateRequest newAccount) {
        if (newAccount.getUserId() == null) {
            return ResponseEntity.badRequest().body("User ID must not be null");
        }
        Account account = accountService.createAccount(newAccount);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }


    @GetMapping("/{accountId}")
    public Account getOneAccount(@PathVariable Long accountId) {
        return accountService.getOneAccount(accountId);
    }

    @PutMapping("/deposit/{accountId}")
    public Account depositOneAccount(@PathVariable Long accountId, @RequestBody AccountDepositRequest accountDepositRequest) {
        return accountService.depositOneAccount(accountId, accountDepositRequest);
    }

    @PutMapping("/withdrawal/{accountId}")
    public Account withdrawalAccount(@PathVariable Long accountId, @RequestBody AccountWithdrawalRequest accountWithdrawalRequest) {
        return accountService.withdrawalAccount(accountId, accountWithdrawalRequest);
    }


    @DeleteMapping("/{accountId}")
    public void deleteOneAccount(@PathVariable Long accountId) {
        accountService.deleteOneAccount(accountId);

    }

}