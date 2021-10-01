package com.example.hmily.mapper;

import com.example.hmily.entity.AccountInfo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccountInfoMapper {
    @Update("update account_info set frozen_balance = frozen_balance + #{frozenBalance} " +
            "where account_name = #{accountName}")
    int increaseAccountBalance(String accountName,Double frozenBalance);

    @Update("update account_info set account_balance = account_balance + #{frozenBalance} , frozen_balance = frozen_balance - #{frozenBalance} " +
            "where frozen_balance >= #{frozenBalance} and account_name = #{accountName}")
    int confirmAccountBalance(String accountName,Double frozenBalance);

    @Update("update account_info set frozen_balance = frozen_balance - #{frozenBalance} " +
            "where frozen_balance >= #{frozenBalance} and account_name = #{accountName}")
    int cancelAccountBalance(String accountName,Double frozenBalance);

    @Select("select id as 'id',account_name as 'accountName',account_balance as 'accountBalance', frozen_balance as 'frozenBalance'" +
            " from account_info where account_name = #{accountName}")
    AccountInfo selectByName(String accountName);
}
