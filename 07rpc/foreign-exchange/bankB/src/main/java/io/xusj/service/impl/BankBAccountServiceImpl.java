package io.xusj.service.impl;

import io.xusj.entity.AccountDO;
import io.xusj.entity.FrozenDO;
import io.xusj.mapper.AccountInfoMapper;
import io.xusj.service.BankBAccountService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("bankBAccountService")
@Slf4j
public class BankBAccountServiceImpl implements BankBAccountService {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
//    @Transactional
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public boolean increaseAccountBalance(String to, double amount) {
        //钱汇出人民币账户
        AccountDO outAccountDO = new AccountDO();
        outAccountDO.setAccountNo("B_RMB");
        outAccountDO.setBalance(amount * 7);
        outAccountDO.setUserId(to);

        int result1 = accountInfoMapper.decreaseAccount(outAccountDO);
        if (result1==0){
            throw new RuntimeException("账户金额不足，少于："+amount);
        }

        // 冻结人民币金额
        FrozenDO frozenDO = new FrozenDO();
        frozenDO.setBalance(amount * 7);//美元转人民币
        frozenDO.setOutAccountNo("B_RMB");
        frozenDO.setUserId(to);
        int i = accountInfoMapper.insertFrozen(frozenDO);

        log.info("******** BankB Service Begin try ...");
        return true;

    }

//    @Transactional
    public void confirmMethod(String to,  double amount) {
        //取消人民币冻结金额
        accountInfoMapper.deleteOutFrozen(to, "B_RMB", amount * 7);

        //钱汇入美元账户
        AccountDO inAccountDO = new AccountDO();
        inAccountDO.setAccountNo("B_MY");
        inAccountDO.setBalance(amount);
        inAccountDO.setUserId(to);
        accountInfoMapper.increaseAccount(inAccountDO);

        log.info("******** BankB Service commit...  ");
    }

    public void cancelMethod(String to, double amount) {
        //取消人民币冻结金额
        accountInfoMapper.deleteOutFrozen(to, "B_RMB", amount * 7);

        //钱汇入人民币账户
        AccountDO outAccountDO = new AccountDO();
        outAccountDO.setAccountNo("B_RMB");
        outAccountDO.setBalance(amount * 7);
        outAccountDO.setUserId(to);
        accountInfoMapper.increaseAccount(outAccountDO);
        log.info("******** BankB Service begin cancel...  ");

    }
}
