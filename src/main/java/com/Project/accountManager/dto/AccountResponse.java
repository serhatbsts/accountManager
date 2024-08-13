package com.Project.accountManager.dto;
import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class AccountResponse {
    int accountNumber;
    String userName;
    int money;

}
