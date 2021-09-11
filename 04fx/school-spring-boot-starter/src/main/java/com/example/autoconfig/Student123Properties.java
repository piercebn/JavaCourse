package com.example.autoconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "student.student123")
@Getter
@Setter
public class Student123Properties {
    private int id = 1;
    private String name = "KK1";
}
