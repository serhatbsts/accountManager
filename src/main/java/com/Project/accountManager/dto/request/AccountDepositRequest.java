package com.Project.accountManager.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDepositRequest {
    BigDecimal depositAmount;
}