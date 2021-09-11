# 第五周作业

## 作业题目

**1.（选做）**使 Java 里的动态代理，实现一个简单的 AOP。
**2.（必做）**写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 GitHub。

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

>  Student/Klass/School 实现自动配置和 Starter
>
> 应用程序引入school-spring-boot-starter
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/app/pom.xml
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/app/src/main/resources/application.properties
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/app/src/main/java/com/example/app/App.java
>
> ```java
> 1.pom.xml中添加
> <dependency>
>   <groupId>com.example.autoconfig</groupId>
>   <artifactId>school-spring-boot-starter</artifactId>
>   <version>1.0-SNAPSHOT</version>
> </dependency>
> 2.application.properties中属性配置
> # school-spring-boot-starter
> student.enabled=true
> student.student100.id=100
> student.student100.name=KK100
> student.student123.id=123
> student.student123.name=KK123
> 3.程序中自动注入使用
> @Resource(name = "student100")
> Student student;
> @Resource(name = "Klass")
> Klass klass;
> @Resource(name = "School")
> School school;
> ```
>
> 构建school-spring-boot-starter
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/school-spring-boot-starter/src/main/java/com/example/autoconfig/SchoolAutoConfiguration.java
>
> ```java
> @Configuration
> @EnableConfigurationProperties({Student100Properties.class,Student123Properties.class})
> @ConditionalOnProperty(prefix = "student",name = "enabled", havingValue = "true", matchIfMissing = true)
> public class SchoolAutoConfiguration {
> 
>     @Autowired
>     private Student100Properties student100Properties;
> 
>     @Autowired
>     private Student123Properties student123Properties;
> 
>     @Bean(name = "student123")
>     public Student getStudent123() {
>         Student student = new Student();
>         student.setId(student123Properties.getId());
>         student.setName(student123Properties.getName());
>         return student;
>     }
> 
>     @Bean(name = "student100")
>     public Student getStudent100() {
>         Student student = new Student();
>         student.setId(student100Properties.getId());
>         student.setName(student100Properties.getName());
>         return student;
>     }
> 
>     @Bean(name = "Klass")
>     public Klass getKlass() {
>         Klass klass = new Klass();
>         List<Student> list = new ArrayList<>();
>         list.add(getStudent100());
>         list.add(getStudent123());
>         klass.setStudents(list);
>         return klass;
>     }
> 
>     @Bean(name = "School")
>     public School getSchool() {
>         return new School();
>     }
> }
> ```
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/school-spring-boot-starter/src/main/java/com/example/autoconfig/Student100Properties.java
>
> ```java
> @ConfigurationProperties(prefix = "student.student100")
> @Getter
> @Setter
> public class Student100Properties {
>     private int id = 1;
>     private String name = "KK1";
> 
> }
> ```
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/school-spring-boot-starter/src/main/resources/META-INF/spring.factories
>
> ```properties
> org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.autoconfig.SchoolAutoConfiguration
> ```

### 作业10

> 使用 JDBC 原生接口，使用事务，PrepareStatement 方式，实现数据库的增删改查操作
>
> 数据库配置，需要本地数据库提前配置好
>
> ```properties
> spring.datasource.url = jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
> spring.datasource.username = root
> spring.datasource.password = 123456
> spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
> # Hikari will use the above plus the following to setup connection pooling
> spring.datasource.type=com.zaxxer.hikari.HikariDataSource
> spring.datasource.hikari.minimum-idle=5
> spring.datasource.hikari.maximum-pool-size=15
> spring.datasource.hikari.auto-commit=true
> spring.datasource.hikari.idle-timeout=30000
> spring.datasource.hikari.pool-name=DatebookHikariCP
> spring.datasource.hikari.max-lifetime=1800000
> spring.datasource.hikari.connection-timeout=30000
> spring.datasource.hikari.connection-test-query=SELECT 1
> ```
>
> 使用Hikari 连接池或JDBC 原生接口获取Connection
>
> ```java
> // 使用Hikari连接池，如不指定spring.datasource.type默认为HikariDataSource
> @Autowired
> private DataSource ds;
> 
> // 使用JDBC原生接口
> @Value("${spring.datasource.driver-class-name}")
> private String driver;
> 
> @Value("${spring.datasource.url}")
> private String url;
> 
> @Value("${spring.datasource.username}")
> private String user;
> 
> @Value("${spring.datasource.password}")
> private String password;
> 
> Connection connection = null;
> 
> public Connection prepareConnection(boolean isUsePool) {
> if (isUsePool) {
>  System.out.println(ds.getClass().getName());
>  try {
>    //使用线程池
>    connection = ds.getConnection();
>  } catch (SQLException e) {
>    e.printStackTrace();
>  }
> } else {
>  try {
>    //读取驱动
>    Class.forName(driver);
>    connection = DriverManager.getConnection(url, user, password);
>  } catch (Exception e) {
>    e.printStackTrace();
>  }
> }
> return connection;
> }
> ```
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/app/src/main/java/com/example/jdbc/DBApplication.java
>
> https://github.com/piercebn/JavaCourse/blob/main/04fx/app/src/main/java/com/example/jdbc/JDBCUtils.java





