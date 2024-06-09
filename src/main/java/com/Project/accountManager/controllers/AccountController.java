package com.Project.accountManager.controllers;

import com.Project.accountManager.dto.AccountResponse;
import com.Project.accountManager.entities.Account;
import com.Project.accountManager.dto.request.AccountCreateRequest;
import com.Project.accountManager.dto.request.AccountDepositRequest;
import com.Project.accountManager.dto.request.AccountWithdrawalRequest;
import com.Project.accountManager.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user_accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccount(@RequestParam Optional<Long> userId) {
        return accountService.getAllAccount(userId);
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountCreateRequest newAccount) {
        return ResponseEntity.ok(accountService.createAccount(newAccount));
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