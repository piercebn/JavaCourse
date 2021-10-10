package io.xusj.service.impl;

import io.xusj.entity.AccountDO;
import io.xusj.entity.FrozenDO;
import io.xusj.mapper.AccountInfoMapper;
import io.xusj.service.BankAAccountService;
import io.xusj.service.BankBAccountService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("bankAAccountService")
@Slf4j
public class BankAAccountServiceImpl implements BankAAccountService {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private BankBAccountService bankBAccountService;

    @Override
//    @Transactional
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public Boolean decreaseBalance(String from, String to, double amount) {
        //在bankA，指定from账户，汇出美元，汇入人民币
        //钱汇出美元账户
        AccountDO outAccountDO = new AccountDO();
        outAccountDO.setAccountNo("A_MY");
        outAccountDO.setBalance(amount);
        outAccountDO.setUserId(from);

        //将钱从美元账户转出
        int result1 = accountInfoMapper.decreaseAccount(outAccountDO);
        if (result1==0){
            throw new RuntimeException("账户金额不足，少于："+amount);
        }

        // 冻结美元金额
        FrozenDO frozenDO = new FrozenDO();
        frozenDO.setBalance(amount);//美元转人民币
        frozenDO.setOutAccountNo("A_MY");
        frozenDO.setUserId(from);
        int i = accountInfoMapper.insertFrozen(frozenDO);

        //远程调用bankB，指定to账户，汇入美元，汇出人民币
        if (!bankBAccountService.increaseAccountBalance(to, amount)) {
            throw new HmilyRuntimeException("bank2Client exception");
        }

        log.info("******** BankA Service  end try...  ");

        return Boolean.TRUE;
    }

    public boolean confirmMethod(String from, String to, double amount) {
        //取消美元冻结
        accountInfoMapper.deleteOutFrozen(from, "A_MY", amount);

        //钱汇入人民币账户
        AccountDO inAccountDO = new AccountDO();
        inAccountDO.setAccountNo("A_RMB");
        inAccountDO.setBalance(amount * 7);
        inAccountDO.setUserId(from);
        accountInfoMapper.increaseAccount(inAccountDO);

        log.info("******** BankA Service end commit...");
        return Boolean.TRUE;
    }

//    @Transactional
    public boolean cancelMethod(String from, String to, double amount) {
        //取消美元冻结
        accountInfoMapper.deleteOutFrozen(from, "A_MY", amount);

        //恢复美元账户金额
        AccountDO outAccountDO = new AccountDO();
        outAccountDO.setAccountNo("A_MY");
        outAccountDO.setBalance(amount);
        outAccountDO.setUserId(from);

        //恢复美元账户金额
        accountInfoMapper.increaseAccount(outAccountDO);

        log.info("******** BankA Service end rollback...  ");
        return Boolean.TRUE;
    }

}
