package com.Project.accountManager.controllers;

import com.Project.accountManager.entities.Account;
import com.Project.accountManager.services.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountServices accountServices;

    public AccountController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountServices.getAllAccount();
    }
    @PostMapping
    public Account createAccount(@RequestBody Account newAccount){
        return accountServices.saveOneAccount(newAccount);
    }
    @GetMapping("/{accountId}")
    public Account getOneAccount(@PathVariable Long accountId){
        return accountServices.getOneAccount(accountId);
    }
  @PutMapping("/withdrawFromAccount/{accountId}")
    public Account withdrawFromAccount(@PathVariable Long accountId,@RequestBody int withdrawAmount){
        return accountServices.withdrawFromAccount(accountId,withdrawAmount);
    }
   @PutMapping("/addFundsToAccount/{accountId}")
    public Account addFundsToAccount(@PathVariable Long accountId,@RequestBody int depositAmount){
        return accountServices.withdrawFromAccount(accountId,depositAmount);
    }
}
