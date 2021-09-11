package com.example.autoconfig;

import lombok.Data;

import java.util.List;

@Data
public class Klass {

    List<Student> students;

    public void dong(){
        System.out.println(this.getClass()+" : "+this.getStudents());
    }

}
