package com.example.autoconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "student.student100")
@Getter
@Setter
public class Student100Properties {
    private int id = 1;
    private String name = "KK1";

}
