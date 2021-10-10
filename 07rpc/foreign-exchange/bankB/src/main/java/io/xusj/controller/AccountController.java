package io.xusj.controller;

import io.xusj.service.BankBAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankB")
public class AccountController {
    @Autowired
    private BankBAccountService bankBAccountService;
    @GetMapping("/hi")
    public String hello(){
        return "hi,this is bankB!";
    }

    @RequestMapping("/transfer")
    public Boolean transfer() {
        this.bankBAccountService.increaseAccountBalance("B",100);
        return true;
    }
}
