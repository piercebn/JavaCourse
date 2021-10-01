# 第八周作业

## 作业题目

**2.（必做）**设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

**6.（必做）**基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。

## 作业完成说明

### 作业2

> 设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。
>
> 基于apache-shardingsphere-5.0.0-beta-shardingsphere-proxy-bin进行演示
>
> 1.初始化sql如下
>
> https://github.com/piercebn/JavaCourse/blob/main/05sql/shardingsphere/init.sql
>
> 2.shardingsphere-proxy配置文件如下
>
> https://github.com/piercebn/JavaCourse/blob/main/05sql/shardingsphere/server.yaml
>
> https://github.com/piercebn/JavaCourse/blob/main/05sql/shardingsphere/config-sharding.yaml
>
> 3.增删改查操作演示sql如下
>
> https://github.com/piercebn/JavaCourse/blob/main/05sql/shardingsphere/operation.sql
>
> 4.shardingsphere-proxy日志输出如下
>
> https://github.com/piercebn/JavaCourse/blob/main/05sql/shardingsphere/stdout.log.txt
>
> 

### 作业6

> 基于 hmily TCC 实现一个简单的分布式事务应用 demo
>
> 1.基于springcloud的demo
>
> https://github.com/piercebn/JavaCourse/blob/main/06db/hmily-springcloud-demo
>
> 1.1初始化数据库
>
> https://github.com/piercebn/JavaCourse/blob/main/06db/hmily-springcloud-demo/sql/init.sql
>
> 1.2启动服务
>
> ```
> run Bank2ServerApplication
> run Bank1ServerApplication
> run EurakeServerApplication
> ```
>
> 1.3测试接口
>
> ```
> http://127.0.0.1:8884/swagger-ui.html#!/account-controller/transferUsingPOST
> 1.用户（bn）存在，正常转账（150，Bank1->Bank2）
> http://127.0.0.1:8884/bank1/transfer?name=bn&amout=150
> 2.用户（abc）不存存在，扣减Bank1（150）失败
> http://127.0.0.1:8884/bank1/transfer?name=abc&amout=150
> 3.用户（bn）存在，增加Bank2（600）大于500失败
> http://127.0.0.1:8884/bank1/transfer?name=bn&amout=600
> 4.用户（bn）存在，增加Bank2（450）大于400超时失败
> http://127.0.0.1:8884/bank1/transfer?name=bn&amout=450
> 问题：Bank2处理超时，Bank1正常cancel，Bank2的try处理完后，后续没有自动cancel补偿处理
> ```
>
> 









