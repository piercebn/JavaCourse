package com.example.app;

import com.example.autoconfig.Klass;
import com.example.autoconfig.School;
import com.example.autoconfig.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    // ==== 测试自动配置 ====
    @Autowired
    WebInfo info;

    // ==== 测试通过starter自动配置 ====
    @Resource(name = "student100")
    Student student;

    @Resource(name = "Klass")
    Klass klass;

    @Resource(name = "School")
    School school;

    @Bean
    public void printInfo(){
        System.out.println(info.getName());
        student.print();
        klass.dong();
        school.ding();
    }

}
