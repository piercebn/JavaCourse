package com.example.hmily.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountInfoMapperTest {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Test
    public void decreaseBalance(){
        int result = accountInfoMapper.decreaseBalance("ls",100d);
        System.out.println(result);
    }
}
