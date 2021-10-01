package com.example.hmily.controller;

import com.example.hmily.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank2")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @GetMapping("/hi")
    public String hello(){
        return "hi,this is bank2!";
    }

    @PostMapping("/transfer")
    public Boolean transfer(@RequestParam("name")String name, @RequestParam("amount") Double amount) {
        return accountService.increaseAccountBalance(name, amount);
    }
}
