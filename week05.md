# 第五周作业

## 作业题目

**1.（选做）**使 Java 里的动态代理，实现一个简单的 AOP。
**2.（必做）**写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 GitHub。
**3.（选做）**实现一个 Spring XML 自定义配置，配置一组 Bean，例如：Student/Klass/School。

**8.（必做）**给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。
**9.（选做**）学习 MyBatis-generator 的用法和原理，学会自定义 TypeHandler 处理复杂类型。
**10.（必做）**研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
1）使用 JDBC 原生接口，实现数据库的增删改查操作。
2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
3）配置 Hikari 连接池，改进上述操作。提交代码到 GitHub。

## 作业完成说明

### 作业2

> 实现 Spring Bean 的装配
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/spring01/src/main/java/io/kimmking/spring02/SpringBeanDemo.java
>
> 方式1:XML配置
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/spring01/src/main/java/io/kimmking/spring02/SpringBean01.java
>
> ```xml
> <bean id="SpringBeanDemo"
>       class="io.kimmking.spring02.SpringBeanDemo">
> </bean>
> <bean id="SpringBean01XML"
>       class="io.kimmking.spring02.SpringBean01">
>   <property name="description" value="Config from ApplicationContext XML" />
> </bean>
> ```
>
> ```Java
> @Data
> public class SpringBean01 {
> 
>     private String description;
> 
>     public void print(){
>         System.out.println("SpringBean01 XML装配:"+description);
>     }
> }
> ```
>
> 方式2:Annotation配置
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/spring01/src/main/java/io/kimmking/spring02/SpringBean02.java
>
> ```Java
> @Component
> public class SpringBean02 {
>     public void print(){
>         System.out.println("SpringBean02 Annotation装配");
>     }
> }
> ```
>
> 方式3:Java Config配置
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/spring01/src/main/java/io/kimmking/spring02/SpringBeanConfig.java
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/spring01/src/main/java/io/kimmking/spring02/SpringBean03.java
>
> ```Java
> @Configuration
> public class SpringBeanConfig {
>     @Bean
>     SpringBean03 getBean() {
>         return new SpringBean03();
>     }
> }
> public class SpringBean03 {
>     public void print(){
>         System.out.println("SpringBean03 Java Config装配");
>     }
> }
> ```
>
> 方式4:Condition配置
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/spring01/src/main/java/io/kimmking/spring02/SpringBeanConfig.java
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/spring01/src/main/java/io/kimmking/spring02/SpringBeanCondition.java
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/spring01/src/main/java/io/kimmking/spring02/SpringBean04.java
>
> ```java
> @Configuration
> public class SpringBeanConfig {
>     @Bean
>     @Conditional(SpringBeanCondition.class)
>     SpringBean04 getConditionBean() {
>         return new SpringBean04();
>     }
> }
> public class SpringBeanCondition implements Condition {
>     @Override
>     public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
>         return true;
>     }
> }
> public class SpringBean04 {
>     public void print(){
>         System.out.println("SpringBean04 Condition装配");
>     }
> }
> ```
>
> 打印测试
>
> ```Java
> public class SpringBeanDemo {
> 
>     @Resource(name = "SpringBean01XML")
>     SpringBean01 springBean01;
> 
>     @Autowired
>     SpringBean02 springBean02;
> 
>     @Autowired
>     SpringBean03 springBean03;
> 
>     @Autowired
>     SpringBean04 springBean04;
> 
>     public static void main(String[] args) {
>         ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
> 
>         SpringBeanDemo springBeanDemo = (SpringBeanDemo) context.getBean("SpringBeanDemo");
>         springBeanDemo.springBean01.print();
>         springBeanDemo.springBean02.print();
>         springBeanDemo.springBean03.print();
>         springBeanDemo.springBean04.print();
>     }
> }
> 
> //输入结果
> SpringBean01 XML装配:Config from ApplicationContext XML
> SpringBean02 Annotation装配
> SpringBean03 Java Config装配
> SpringBean04 Condition装配
> ```



### 作业8



### 作业10







