package com.Project.accountManager.request;

import lombok.Data;

@Data
public class AccountCreateRequest {
    Long id;
    int money;
    int accountNumber;
    Long userId;

}
