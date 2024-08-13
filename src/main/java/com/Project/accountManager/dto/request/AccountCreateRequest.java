package com.Project.accountManager.dto.request;

import lombok.Data;

@Data
public class AccountCreateRequest {
    Long id;
    int accountNumber;
    int money;
    Long userId;
}