package com.Project.accountManager.services;

import com.Project.accountManager.entities.Account;
import com.Project.accountManager.entities.User;
import com.Project.accountManager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServices {
    @Autowired
    AccountRepository accountRepository;

    public AccountServices(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account saveOneAccount(Account newAccount){
        return accountRepository.save(newAccount);
    }

    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }

    public Account getOneAccount(Long accountId){
        return accountRepository.findById(accountId).orElse(null);
    }

    public Account addFundsToAccount(Long accountId,int depositAmount){
       Optional<Account> account=accountRepository.findById(accountId);
       if (account.isPresent()){
           Account foundAccount=account.get();
           foundAccount.setMoney(foundAccount.getMoney()+depositAmount);
           accountRepository.save(foundAccount);
           return foundAccount;
       }
       else return null;
    }
    public Account withdrawFromAccount(Long accountId,int withdrawAmount){
        Optional<Account> account=accountRepository.findById(accountId);
        if (account.isPresent()){
            Account foundAccount=account.get();
            foundAccount.setMoney(foundAccount.getMoney()-withdrawAmount);
            accountRepository.save(foundAccount);
            return foundAccount;
        }
        else {
            //custom exception ekle
            return null;
        }
    }

    public void deleteById(Long accountId){
        accountRepository.deleteById(accountId);
    }



}
