package com.Project.accountManager.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreateRequest {
    BigDecimal balance;
    Long userId;
}