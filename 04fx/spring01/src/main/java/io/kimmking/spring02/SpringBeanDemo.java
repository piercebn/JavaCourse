package io.kimmking.spring02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

public class SpringBeanDemo {

    @Resource(name = "SpringBean01XML")
    SpringBean01 springBean01;

    @Autowired
    SpringBean02 springBean02;

    @Autowired
    SpringBean03 springBean03;

    @Autowired
    SpringBean04 springBean04;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        SpringBeanDemo springBeanDemo = (SpringBeanDemo) context.getBean("SpringBeanDemo");
        springBeanDemo.springBean01.print();
        springBeanDemo.springBean02.print();
        springBeanDemo.springBean03.print();
        springBeanDemo.springBean04.print();

    }
}
