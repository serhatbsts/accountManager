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
public class AccountServices {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserServices userServices;

    public AccountServices(AccountRepository accountRepository, UserServices userServices) {
        this.accountRepository = accountRepository;
        this.userServices = userServices;
    }

    public Account saveOneAccount(AccountCreateRequest newAccount){
        User user=userServices.getOneUser(newAccount.getUserId());
        if(user==null) {
            return null;
        }
        Account toSave=new Account();
        toSave.setId(newAccount.getId());
        toSave.setAccountNumber(newAccount.getAccountNumber());
        toSave.setMoney(newAccount.getMoney());
        toSave.setUser(user);
        return accountRepository.save(toSave);
    }

    public List<Account> getAllAccount(Optional<Long> userId){
        if (userId.isPresent()){
            return accountRepository.findByUserId(userId.get());
        }else {
            return accountRepository.findAll();
        }
    }

    public Account getOneAccount(Long accountId){
        return accountRepository.findById(accountId).orElse(null);
    }

    public Account addFundsToAccount(Long accountId, AccountUpdateRequest accountUpdateRequest){
      /* Optional<Account> account=accountRepository.findById(accountId);
       if (account.isPresent()){
           Account foundAccount=account.get();
          foundAccount.setMoney(foundAccount.getMoney()+depositAmount);
           accountRepository.save(foundAccount);
           return foundAccount;
       }
       else return null;

       */

        Optional<Account> account=accountRepository.findById(accountId);
        if (account.isPresent()){
            Account toUpdate=account.get();
            toUpdate.setMoney(accountUpdateRequest.getMoney());
            accountRepository.save(toUpdate);
            return toUpdate;
        }else return null;




    }
   /* public Account withdrawFromAccount(Long accountId,int withdrawAmount){
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

    */

    public void deleteById(Long accountId){
        accountRepository.deleteById(accountId);
    }



}
