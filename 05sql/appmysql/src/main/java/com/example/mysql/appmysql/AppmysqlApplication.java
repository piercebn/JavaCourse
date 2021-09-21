package com.example.mysql.appmysql;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@MapperScan("com.example.mysql.appmysql.dao")
public class AppmysqlApplication {

    @Autowired
    JDBCUtils jdbcUtils;

    public static void main(String[] args) {
        SpringApplication.run(AppmysqlApplication.class, args);
    }

    @Bean
    public void printInfo() throws InterruptedException {

        intestTest1();

        intestTest2();

        intestTest3();

        intestTest4();

    }

    private void intestTest1(){
        System.out.println("Test1 开始");
        long start = System.currentTimeMillis();
        insertBatch(1, 1000000);
        long end = System.currentTimeMillis();
        System.out.println("Test1 总耗时：" + (end - start)/1000 + "秒");
    }

    private void intestTest2(){
        System.out.println("Test2 开始");
        long start = System.currentTimeMillis();
        int count = 4;
        int batch = 1000000/4;
        for(int i = 0; i < count; i++){
            insertBatch(i+1, batch);
        }
        long end = System.currentTimeMillis();
        System.out.println("Test2 总耗时：" + (end - start)/1000 + "秒");
    }

    private void intestTest3() throws InterruptedException {
        System.out.println("Test3 开始");
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(4);
        int count = 4;
        int batch = 1000000/4;
        for(int i = 0; i < count; i++){
            int finalI = i;
            new Thread(() -> {
                insertBatch(finalI +1, batch);
                latch.countDown();
            }).start();
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("Test3 总耗时：" + (end - start)/1000 + "秒");
    }

    private void intestTest4() throws InterruptedException {
        System.out.println("Test4 开始");
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(10);
        int count = 10;
        int batch = 1000000/10;
        for(int i = 0; i < count; i++){
            int finalI = i;
            new Thread(() -> {
                insertBatch(finalI +1, batch);
                latch.countDown();
            }).start();
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("Test4 总耗时：" + (end - start)/1000 + "秒");
    }

    private void insertBatch(Integer batchId, Integer batchCount){
        System.out.println("第"+batchId+"批添加开始: " + batchCount+ "条");
        long start = System.currentTimeMillis();

        Connection con = jdbcUtils.prepareConnection(false);
        PreparedStatement pstmt = null;

        try {
            //关闭事务自动提交
            con.setAutoCommit(false);

            String sql = "insert into user values(default,?,?,?,?,?,?,?,?,?)";
            //获取执行sql的对象PreparedStatement
            pstmt = con.prepareStatement(sql);

            for (int i = 0; i < batchCount; i++) {
                pstmt.setString(1,(int)(Math.random()*1000000)+"");
                pstmt.setString(2,(int)(Math.random()*1000000)+"");
                pstmt.setString(3,(int)(Math.random()*1000000)+"");
                pstmt.setInt(4,(int)(Math.random()*2));
                pstmt.setString(5,"");
                pstmt.setInt(6,(int)(Math.random()*61));
                pstmt.setInt(7,(int)(Math.random()*5));
                pstmt.setLong(8, System.currentTimeMillis());
                pstmt.setLong(9, System.currentTimeMillis());

                pstmt.addBatch();
            }
            //往数据库插入一次数据
            pstmt.executeBatch();
            //提交事务
            con.commit();
            System.out.println("第"+batchId+"批添加成功！");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("第"+batchId+"批添加失败！");
            try {
                con.rollback();//回滚事务
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            jdbcUtils.releaseConnection(con);
        }

        long end = System.currentTimeMillis();
        System.out.println("第"+batchId+"批耗时：" + (end - start)/1000 + "秒");
    }

}
