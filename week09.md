# 第九周作业

## 作业题目

**3.（必做）**改造自定义 RPC 的程序，提交到 GitHub：

- 尝试将服务端写死查找接口实现类变成泛型和反射；
- 尝试将客户端动态代理改成 AOP，添加异常处理；
- 尝试使用 Netty+HTTP 作为 client 端传输方式。

**7.（必做）**结合 dubbo+hmily，实现一个 TCC 外汇交易处理，代码提交到 GitHub:

- 用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;
- 用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;
- 设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。

## 作业完成说明

### 作业3

> 改造自定义 RPC 的程序
>
> https://github.com/piercebn/JavaCourse/tree/main/07rpc/rpc01



### 作业7

> 结合 dubbo+hmily，实现一个 TCC 外汇交易处理
>
> https://github.com/piercebn/JavaCourse/tree/main/07rpc/foreign-exchange
>
> 1初始化数据库
>
> 为用户A创建A库，为用户B创建B库，并分别创建账户表和冻结资产表
>
> https://github.com/piercebn/JavaCourse/blob/main/07rpc/foreign-exchange/sql/init.sql
>
> 2启动服务
>
> ```
> 启动ZooKeeper
> run BankAApp
> run BankBApp
> ```
>
> 3测试接口
>
> ```
> http://localhost:8761/swagger-ui.html#!/account-controller/transferNestUsingPOST
> 用户A在BankA，汇出美元账户100美元，汇入人民币账户700人民币，用户B在BankB，汇出人民币账户700人民币，汇入美元账户100美元
> http://localhost:8761/bank1/transfer
> ```
>





