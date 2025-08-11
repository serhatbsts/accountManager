package com.Project.accountManager.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Random;


@Entity
@Table(name = "user_accounts")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    BigDecimal balance;
    @Column(unique = true)
    private String accountNumber;

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

     String generateAccountNumber() {
        // Örneğin: 10 basamaklı rastgele bir sayı oluşturma
        Random random = new Random();
        StringBuilder accountNumberBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            accountNumberBuilder.append(digit);
        }
        return accountNumberBuilder.toString();
    }

}