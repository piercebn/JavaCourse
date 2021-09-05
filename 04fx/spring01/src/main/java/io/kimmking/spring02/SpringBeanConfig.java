package io.kimmking.spring02;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeanConfig {

    @Bean
    SpringBean03 getBean() {
        return new SpringBean03();
    }

    @Bean
    @Conditional(SpringBeanCondition.class)
    SpringBean04 getConditionBean() {
        return new SpringBean04();
    }
}
