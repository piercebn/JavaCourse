package com.example.hmily.controller;

import com.example.hmily.entity.AccountInfo;
import com.example.hmily.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank1")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/transfer")
    @ApiOperation(value = "转账接口（注意这里模拟的是将某个账户的金额从Bank1转到Bank2操作）")
    public Boolean transfer(@RequestParam("name")String name,@RequestParam("amout")Double amout) {
        return accountService.decreaseBalance(name, amout);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取某个账户的信息")
    public AccountInfo get(@RequestParam("name") String name){
        return accountService.selectByName(name);
    }


}
