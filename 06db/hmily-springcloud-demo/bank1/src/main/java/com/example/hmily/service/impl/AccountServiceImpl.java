package com.example.hmily.service.impl;

import com.example.hmily.entity.AccountInfo;
import com.example.hmily.feign.Bank2Client;
import com.example.hmily.mapper.AccountInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import com.example.hmily.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private Bank2Client bank2Client;

    @Override
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public Boolean decreaseBalance(String name, Double amount) {
        log.info("******** Bank1 Service begin try...  ");
        //从账户扣减
        if (accountInfoMapper.decreaseBalance(name, amount) <= 0) {
            //扣减失败
            throw new HmilyRuntimeException("bank1 exception，扣减失败");
        }
        //远程调用bank2
        try {
            bank2Client.transfer(name, amount);
        } catch (Exception e) {
            //异常一定要抛在Hmily里面
            throw new HmilyRuntimeException("bank2Client exception : " + e.getMessage());
        }
        log.info("******** Bank1 Service  end try...  ");

        return Boolean.TRUE;
    }

    @Override
    public AccountInfo selectByName(String accountName) {
        return accountInfoMapper.selectByName(accountName);
    }


    public boolean confirmMethod(String name, Double amount) {
        log.info("******** Bank1 Service begin commit...");
        return accountInfoMapper.confirm(name, amount) > 0;
    }

    public boolean cancelMethod(String name, Double amount) {
        log.info("******** Bank1 Service begin rollback...  ");
        return accountInfoMapper.cancel(name, amount) > 0;
    }

}
