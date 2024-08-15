package com.Project.accountManager.dto.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
public class AccountCreateRequest {
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  Long id;
  //  int accountNumber;
    int money;
    Long userId;
}