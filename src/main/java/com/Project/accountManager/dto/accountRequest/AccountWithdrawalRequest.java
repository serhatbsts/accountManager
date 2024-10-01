package com.Project.accountManager.dto.accountRequest;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountWithdrawalRequest {
    BigDecimal withdrawalAmount;
}