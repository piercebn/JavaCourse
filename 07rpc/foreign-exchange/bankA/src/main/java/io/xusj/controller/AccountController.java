package io.xusj.controller;

import io.xusj.service.BankAAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank1")
public class AccountController {
    @Autowired
    private BankAAccountService accountService;


    @PostMapping("/transfer")
    public Boolean transferNest() {
        return accountService.decreaseBalance("A","B", 100);
    }


}
