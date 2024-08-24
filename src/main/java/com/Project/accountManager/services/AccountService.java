package com.Project.accountManager.services;

import com.Project.accountManager.dto.accountRequest.AccountCreateRequest;
import com.Project.accountManager.entities.Account;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.AccountRepository;
import com.Project.accountManager.dto.accountRequest.AccountDepositRequest;
import com.Project.accountManager.dto.accountRequest.AccountWithdrawalRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }


    public Account createAccount(AccountCreateRequest newAccount) {
        Optional<User> optionalUser = userService.getUserById(newAccount.getUserId());

        User user = optionalUser.get();
        Account toSave = new Account();
        toSave.setBalance(newAccount.getBalance());
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

    public Account depositOneAccount(Long accountId, AccountDepositRequest accountDepositRequest) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            Account toUpdate = account.get();
            toUpdate.setBalance(toUpdate.getBalance().add(accountDepositRequest.getDepositAmount()));
            accountRepository.save(toUpdate);
            return toUpdate;
        } else return null;            //custom exception add
    }

    public Account withdrawalAccount(Long accountId, AccountWithdrawalRequest accountWithdrawalRequest) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            Account toUpdate = account.get();
            //the amount of money withdrawn may be more than the amount in the account, correct this situation
            toUpdate.setBalance(toUpdate.getBalance().subtract(accountWithdrawalRequest.getWithdrawalAmount()));
            accountRepository.save(toUpdate);
            return toUpdate;
        } else return null;            //custom exception add
    }


    public void deleteOneAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

}