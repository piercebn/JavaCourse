# 第十四周作业

## 作业题目

**4.（选做）**针对课上讲解的内容，自己动手设计一个高并发的秒杀系统，讲架构图， 设计文档等，提交到 GitHub。

## 作业完成说明

### 作业4

#### 秒杀系统技术挑战 

1.瞬间高并发

- 8000 并发：预估秒杀在线人数可达 8000 人 。
- 风险：带宽耗尽。
- 服务器：崩溃，可以理解成自己给自己准备的 D.D.O.S 攻击。 

2.秒杀器

- 第一种：秒杀前不断刷新秒杀页面，直到秒杀开始，抢着下单。
- 第二种：跳过秒杀页面，直接进入下单页面，下单。

#### 秒杀系统架构方案设计原则

1.静态化

- 采用 JS 自动更新技术将动态页面转化为静态页面 

2.并发控制，防秒杀器

- 设置阀门，只放最前面的一部分人进入秒杀系统 

3.简化流程

- 砍掉不重要的分支流程，如下单页面的所有数据库查询
- 以下单成功作为秒杀成功标志。支付流程只要在1天内完成即可。 

4.前端优化 

- 采用YSLOW原则提升页面响应速度



