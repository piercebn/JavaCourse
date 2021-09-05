package com.example.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BeanAutoConfiguration {

    @Resource(name = "student123")
    Student student123;

    @Resource(name = "student100")
    Student student100;

    @Bean(name = "student123")
    public Student getStudent123(@Value("${student.student123.id}") int id,@Value("${student.student123.name}") String name) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        return student;
    }

    @Bean(name = "student100")
    public Student getStudent100(@Value("${student.student100.id}") int id,@Value("${student.student100.name}") String name) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        return student;
    }

    @Bean
    public Klass getKlass() {
        Klass klass = new Klass();
        List<Student> list = new ArrayList<>();
        list.add(student123);
        list.add(student100);
        klass.setStudents(list);
        return klass;
    }

    @Bean
    public School getSchool() {
        return new School();
    }
}
