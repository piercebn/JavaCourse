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
> 









