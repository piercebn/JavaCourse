package com.example.app;

import lombok.Data;

@Data
public class Student {
    private int id = 1;
    private String name = "KK1";

    public void print(){
        System.out.println("Student:"+name);
    }
}
