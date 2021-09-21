package com.example.mysql.appmysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class JDBCUtils {

    // 使用Hikari连接池，如不指定spring.datasource.type默认为HikariDataSource
    @Autowired
    private DataSource ds;

    // 使用JDBC原生接口
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    public Connection prepareConnection(boolean isUsePool) {
        Connection connection = null;
        if (isUsePool) {
            System.out.println(ds.getClass().getName());
            try {
                //使用线程池
                connection = ds.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                //读取驱动
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
