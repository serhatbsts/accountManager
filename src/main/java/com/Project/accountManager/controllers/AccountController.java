package com.Project.accountManager.controllers;

import com.Project.accountManager.entities.Account;
import com.Project.accountManager.request.AccountCreateRequest;
import com.Project.accountManager.request.AccountUpdateRequest;
import com.Project.accountManager.services.AccountServices;
import com.Project.accountManager.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountServices accountServices;
    @Autowired
    UserServices userServices;

    public AccountController(AccountServices accountServices, UserServices userServices) {
        this.accountServices = accountServices;
        this.userServices = userServices;
    }




    @GetMapping
    public List<Account> getAllAccounts(@RequestParam Optional<Long> userId){
       return accountServices.getAllAccount(userId);
    }
    @PostMapping
    public Account createAccount(@RequestBody AccountCreateRequest newAccount){
        return accountServices.saveOneAccount(newAccount);
    }
    @GetMapping("/{accountId}")
    public Account getOneAccount(@PathVariable Long accountId){
        return accountServices.getOneAccount(accountId);
    }
  @PutMapping("/{accountId}")
    public Account withdrawFromAccount(@PathVariable Long accountId,@RequestBody AccountUpdateRequest accountUpdateRequest){
        return accountServices.addFundsToAccount(accountId,accountUpdateRequest);
    }

}
