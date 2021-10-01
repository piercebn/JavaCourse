package com.example.hmily.service.impl;

import com.example.hmily.mapper.AccountInfoMapper;
import com.example.hmily.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public boolean increaseAccountBalance(String accountName, Double amount) {
        log.info("******** Bank2 Service begin try ...");
        if(amount>500) {
            //转账每次不能大于500
            throw new HmilyRuntimeException("bank1 exception，增加大于500失败");
        }else if(amount>400) {
            //400到500之间模拟超时
            try {
                //模拟延迟 当前线程暂停5秒
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("******** Bank2 Service timeout ready try ...");
        }
        if (accountInfoMapper.increaseAccountBalance(accountName, amount) <= 0) {
            //增加失败
            throw new HmilyRuntimeException("bank1 exception，增加失败");
        }
        log.info("******** Bank2 Service end try ...");
        return Boolean.TRUE;
    }

    public boolean confirmMethod(String accountName, Double amount) {
        log.info("******** Bank2 Service begin commit...  ");
        return accountInfoMapper.confirmAccountBalance(accountName, amount) > 0;
    }

    public boolean cancelMethod(String accountName, Double amount) {
        log.info("******** Bank2 Service begin cancel...  ");
        return accountInfoMapper.cancelAccountBalance(accountName, amount) > 0;
    }
}
