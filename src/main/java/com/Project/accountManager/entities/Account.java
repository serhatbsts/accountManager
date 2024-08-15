package com.Project.accountManager.entities;

import com.Project.accountManager.dto.request.AccountCreateRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "user_accounts")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    int money; //big decimal yap ve money yerine balaceyi kullan
    @Column(unique = true)
    private String accountNumber;//stringe çevir

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;

    @PrePersist
    private void prePersist() {
        if (this.accountNumber == null) {
            this.accountNumber = generateAccountNumber();
        }
    }

    private String generateAccountNumber() {
        // Örneğin: 10 basamaklı rastgele bir sayı oluşturma
        Random random = new Random();
        StringBuilder accountNumberBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10); // 0-9 arasında rastgele bir sayı
            accountNumberBuilder.append(digit);
        }
        return accountNumberBuilder.toString();
    }
   /*
   response kullanılırsa bunu kullan ama response yoksa bunada gerek yok!
   public static Account toEntity(AccountCreateRequest createRequest,User user){
        Account account=new Account();
        account.setMoney(createRequest.getMoney());
        account.setAccountNumber(createRequest.getAccountNumber());
        account.setUser(user);
        return account;
    }
    */
}