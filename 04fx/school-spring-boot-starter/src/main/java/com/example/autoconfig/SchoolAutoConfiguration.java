package com.example.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties({Student100Properties.class,Student123Properties.class})
@ConditionalOnProperty(prefix = "student",name = "enabled", havingValue = "true", matchIfMissing = true)
public class SchoolAutoConfiguration {

    @Autowired
    private Student100Properties student100Properties;

    @Autowired
    private Student123Properties student123Properties;

    @Bean(name = "student123")
    public Student getStudent123() {
        Student student = new Student();
        student.setId(student123Properties.getId());
        student.setName(student123Properties.getName());
        return student;
    }

    @Bean(name = "student100")
    public Student getStudent100() {
        Student student = new Student();
        student.setId(student100Properties.getId());
        student.setName(student100Properties.getName());
        return student;
    }

    @Bean(name = "Klass")
    public Klass getKlass() {
        Klass klass = new Klass();
        List<Student> list = new ArrayList<>();
        list.add(getStudent100());
        list.add(getStudent123());
        klass.setStudents(list);
        return klass;
    }

    @Bean(name = "School")
    public School getSchool() {
        return new School();
    }
}
