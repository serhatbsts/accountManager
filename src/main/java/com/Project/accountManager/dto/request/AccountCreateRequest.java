package com.Project.accountManager.dto.request;

import lombok.Data;

@Data
public class AccountCreateRequest {
    Long id; // you can not request id - please reminder me to discuss why you should pass id when you save a entity
    int accountNumber;
    int money;// money of what ? do you mean balance ?
    Long userId;
}