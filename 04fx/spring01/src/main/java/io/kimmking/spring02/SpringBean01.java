package io.kimmking.spring02;

import lombok.Data;

@Data
public class SpringBean01 {

    private String description;

    public void print(){
        System.out.println("SpringBean01 XML装配:"+description);
    }
}
