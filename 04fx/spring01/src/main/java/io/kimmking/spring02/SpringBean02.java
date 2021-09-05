package io.kimmking.spring02;

import org.springframework.stereotype.Component;

@Component
public class SpringBean02 {

    public void print(){
        System.out.println("SpringBean02 Annotation装配");
    }

}
