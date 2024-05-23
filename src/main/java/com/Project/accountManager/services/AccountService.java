package com.Project.accountManager.services;

import com.Project.accountManager.entities.Account;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.AccountRepository;
import com.Project.accountManager.request.AccountCreateRequest;
import com.Project.accountManager.request.AccountUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserService userService;

    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public Account saveOneAccount(AccountCreateRequest newAccount) {
        User user = userService.getOneUser(newAccount.getUserId());
        if (user == null) {
            return null;
            //custom exception add
        }
        Account toSave = new Account();
        toSave.setId(newAccount.getId());
        toSave.setAccountNumber(newAccount.getAccountNumber());
        toSave.setMoney(newAccount.getMoney());
        toSave.setUser(user);
        return accountRepository.save(toSave);
    }

    public List<Account> getAllAccount(Optional<Long> userId) {
        if (userId.isPresent()) {
            return accountRepository.findByUserId(userId.get());
        } else {
            return accountRepository.findAll();
        }
    }

    public Account getOneAccount(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public Account updateOneAccount(Long accountId, AccountUpdateRequest accountUpdateRequest) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            Account toUpdate = account.get();
            toUpdate.setMoney(accountUpdateRequest.getMoney());
            accountRepository.save(toUpdate);
            return toUpdate;
        } else return null;            //custom exception add
    }

    public void deleteOneAccount(Long accountId){
        accountRepository.deleteById(accountId);
    }

}
