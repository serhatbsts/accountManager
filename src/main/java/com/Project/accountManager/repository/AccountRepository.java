package com.Project.accountManager.repository;

import com.Project.accountManager.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
//List<Account> findByUserId(long userId); //Check again to set up more than one account.
List<Account> findByUserId(Long userId);
}
