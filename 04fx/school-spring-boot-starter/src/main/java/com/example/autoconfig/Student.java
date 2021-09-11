package com.example.autoconfig;

import lombok.Data;

@Data
public class Student {
    private int id;
    private String name;

    public void print(){
        System.out.println(this.getClass()+" Student:"+name);
    }
}
