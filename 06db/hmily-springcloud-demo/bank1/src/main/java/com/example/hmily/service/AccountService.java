package com.example.hmily.service;

import com.example.hmily.entity.AccountInfo;

public interface AccountService {

    Boolean decreaseBalance(String name,Double amount);

    AccountInfo selectByName(String accountName);
}
