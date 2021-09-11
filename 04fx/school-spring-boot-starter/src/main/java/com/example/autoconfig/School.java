package com.example.autoconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
public class School implements ISchool {

    // Resource
    @Autowired(required = true) //primary
    Klass class1;

    @Resource(name = "student100")
    Student student100;

    @Override
    public void ding(){

        System.out.println(this.getClass()+" have " + this.class1.getStudents().size() + " students and one is " + this.student100);

    }

}
