package com.Project.accountManager.entities;

import com.Project.accountManager.dto.request.AccountCreateRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "user_accounts")
@Data
public class Account {
    @Id
    private Long id;
    int money; //big decimal yap ve money yerine balaceyi kullan
    int accountNumber;//stringe çevir

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;

    public static Account toEntity(AccountCreateRequest createRequest,User user){
        Account account=new Account();
        account.setMoney(createRequest.getMoney());
        account.setAccountNumber(createRequest.getAccountNumber());
        account.setUser(user);
        return account;
    }
}