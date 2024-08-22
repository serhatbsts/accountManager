package com.Project.accountManager.dto.accountRequest;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreateRequest {
    BigDecimal balance;
    Long userId;
}