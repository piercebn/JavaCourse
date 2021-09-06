package com.example.jdbc;

import com.example.app.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class DBApplication {

    @Autowired
    JDBCUtils jdbcUtils;

    public static void main(String[] args) {
        SpringApplication.run(DBApplication.class, args);
    }

    @Bean
    public void printInfo(){
        Connection con = jdbcUtils.prepareConnection(true);
        jdbcUtils.recreateTable();
        jdbcUtils.query();
        try {
            con.setAutoCommit(false);//开启事务
            jdbcUtils.insert("张三", 20);
            jdbcUtils.insert("李四", 25);
            jdbcUtils.insert("王五", 30);
            jdbcUtils.query();
            jdbcUtils.delete("李四");
            User user = jdbcUtils.queryByName("张三");
            if (user != null) {
                user.setAge(40);
                jdbcUtils.update(user);
            }
            con.commit();//try的最后提交事务
        } catch (Exception e) {
            try {
                con.rollback();//回滚事务
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        jdbcUtils.query();
        jdbcUtils.releaseConnection();
    }
}
