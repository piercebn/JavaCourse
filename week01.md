

# 第一周作业

## 作业题目

1.（选做）自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码，有问题群里讨论。

2.（必做）自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。

3.（必做）画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。

4.（选做）检查一下自己维护的业务系统的 JVM 参数配置，用 jstat 和 jstack、jmap 查看一下详情，并且自己独立分析一下大概情况，思考有没有不合理的地方，如何改进。

注意：如果没有线上系统，可以自己 run 一个 web/java 项目。

5.（选做）本机使用 G1 GC 启动一个程序，仿照课上案例分析一下 JVM 情况。

## 作业完成说明

### 作业1

Hello.java代码如下：

> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/jvm/Hello.java

```java
public class Hello {
    public static void main(String[] args) {
        int num1 = 1;       // 字面量1;
        double num2 = 2.0D; // 大小写的D都可以
        long num3 = 3L;     // 大小写的L都可以, 加L是好习惯;
        byte num4 = 4;      // 可以直接赋予 [-128, 127] 范围内的字面量;
        if ("".length() < 10) {
            // 错误用法: num2 + num3 = 2.03
            System.out.println("错误用法: num2 + num3 = " + num2 + num3);
        }
        for (int i = 0; i < num1; i++) {
            // 四则运算: num1 * num4 = 4
            System.out.print("四则运算: num1 * num4 = ");
            System.out.println(num1 * num4);
        }
    }
}
```

字节码分析如下：

```java
Compiled from "Hello.java"
public class com.piercebn.javacource.jvm.Hello {
  public com.piercebn.javacource.jvm.Hello();  // 默认无参构造函数
    Code:                                   // stack=1, locals=1, args_size=1
       0: aload_0                           // 加载本地变量表位置0的变量加载到栈上，类型为类Hello引用，栈指针1
       1: invokespecial #1                  // 调用父类构造函数，栈指针0，常量池#1 Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]); // 静态main方法
    Code:                                   // stack=4, locals=8, args_size=1
       0: iconst_1                          // 常量1入栈，类型为int，栈指针1
       1: istore_1                          // 将栈int类型数据存入变量表位置1，栈指针0
       2: ldc2_w        #2                  // double 2.0d，常量#2 2.0d入栈，类型为double，栈指针2
       5: dstore_2                          // 将栈double类型数据存入变量表位置2，栈指针0
       6: ldc2_w        #4                  // long 3l，常量#4 3l入栈，类型为long，栈指针2
       9: lstore        4                   // 将栈long类型数据存入变量表位置4，栈指针0
      11: iconst_4                          // 常量4入栈，类型为int，栈指针1
      12: istore        6                   // 将栈int类型数据存入变量表位置6，栈指针0
      14: ldc           #6                  // String，常量#6 “”入栈，类型为String，栈指针1
      16: invokevirtual #7                  // 调用虚拟方法，结果0入栈，栈指针1，常量池#7 Method java/lang/String.length:()I
      19: bipush        10                  // 常量10入栈，类型为int，栈指针2
      21: if_icmpge     54                  // 字符串长度0 < 10继续执行下调指令，否则跳转到54执行，栈指针0
      24: getstatic     #8                  // 加载静态成员System.out，栈指针1，常量池#8 Field java/lang/System.out:Ljava/io/PrintStream;
      27: new           #9                  // new对象#9 class java/lang/StringBuilder
      30: dup                               // 对象入栈，栈指针2
      31: invokespecial #10                 // 调用构造函数，常量池#10 Method java/lang/StringBuilder."<init>":()V
      34: ldc           #11                 // String 错误用法: num2 + num3 =，常量#11入栈，类型为String，栈指针3
      36: invokevirtual #12                 // 调用虚拟方法追加String，栈指针2，常量池#12 Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      39: dload_2                           // 加载变量表位置2数据入栈，类型double，栈指针4
      40: invokevirtual #13                 // 调用虚拟方法追加double，栈指针2，常量池#13 Method java/lang/StringBuilder.append:(D)Ljava/lang/StringBuilder;
      43: lload         4                   // 加载变量表位置4数据入栈，类型long，栈指针4
      45: invokevirtual #14                 // 调用虚拟方法追加long，栈指针2，常量池#14 Method java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
      48: invokevirtual #15                 // 调用虚拟方法转换String入栈，栈指针2，常量池#15 Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      51: invokevirtual #16                 // 调用虚拟方法打印String，栈指针0，常量池#16 Method java/io/PrintStream.println:(Ljava/lang/String;)V
      54: iconst_0                          // 常量0入栈，类型为int，栈指针1
      55: istore        7                   // 将栈int类型数据存入变量表位置7，栈指针0
      57: iload         7                   // 加载变量表位置7数据入栈，类型int，栈指针1
      59: iload_1                           // 加载变量表位置1数据入栈，类型int，栈指针2
      60: if_icmpge     87                  // 0 < 1继续执行下调指令，否则跳转到87执行，栈指针0
      63: getstatic     #8                  // 加载静态成员System.out，栈指针1，常量池#8 Field java/lang/System.out:Ljava/io/PrintStream;
      66: ldc           #17                 // String 四则运算: num1 * num4 =，常量#17入栈，类型为String，栈指针2
      68: invokevirtual #18                 // 调用虚拟方法打印String，栈指针0，常量池#18 Method java/io/PrintStream.print:(Ljava/lang/String;)V
      71: getstatic     #8                  // 加载静态成员System.out，栈指针1，常量池#8 Field java/lang/System.out:Ljava/io/PrintStream;
      74: iload_1                           // 加载变量表位置1数据入栈，类型int，栈指针2
      75: iload         6                   // 加载变量表位置6数据入栈，类型int，栈指针3
      77: imul                              // 乘法运算结果入栈，类型int，栈指针2
      78: invokevirtual #19                 // 调用虚拟方法打印int，栈指针0，常量池#19 Method java/io/PrintStream.println:(I)V
      81: iinc          7, 1                // int类型变量表7位置的变量自增1，自增后数值为1
      84: goto          57                  // 跳转57执行
      87: return
}
```

### 作业2

自定义 Classloader代码如下：

> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/jvm/XlassLoader.java

### 作业3

Xmx、Xms、Xmn、Meta、DirectMemory、Xss 内存参数关系图如下：

<img src="01jvm/images/image-jvm-memory.png" alt="image-jvm-memory" style="zoom:60%;" />



### 作业4

检查JVM参数配置及相关堆和栈信息

jinfo

> jinfo 24963

```java
java.runtime.name = Java(TM) SE Runtime Environment
java.vm.version = 25.281-b09
sun.boot.library.path = /usr/java/jdk1.8.0_281-amd64/jre/lib/amd64
java.protocol.handler.pkgs = org.springframework.boot.loader
java.vendor.url = http://java.oracle.com/
java.vm.vendor = Oracle Corporation
path.separator = :
file.encoding.pkg = sun.io
java.vm.name = Java HotSpot(TM) 64-Bit Server VM
...
VM Flags:
Non-default VM flags: -XX:CICompilerCount=4 -XX:InitialHeapSize=2147483648 -XX:MaxHeapSize=2147483648 -XX:MaxNewSize=536870912 -XX:MinHeapDeltaBytes=524288 -XX:NewSize=536870912 -XX:OldSize=1610612736 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC
Command line:  -Xms2048m -Xmx2048m -XX:MaxNewSize=512m -XX:MaxPermSize=1024m  
// 使用了已废弃的参数-XX:MaxPermSize
```

 jstat 

> jstat -gcutil -t 24963 1000 10

```java
Timestamp         S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT
        15116.0   4.26   0.00  14.49   3.90  94.48  92.16     22    0.379     3    0.302    0.681
        15117.0   4.26   0.00  14.49   3.90  94.48  92.16     22    0.379     3    0.302    0.681
        15118.0   4.26   0.00  14.50   3.90  94.48  92.16     22    0.379     3    0.302    0.681
        15119.0   4.26   0.00  14.50   3.90  94.48  92.16     22    0.379     3    0.302    0.681
        15120.0   4.26   0.00  14.50   3.90  94.48  92.16     22    0.379     3    0.302    0.681
        15121.0   4.26   0.00  14.72   3.90  94.48  92.16     22    0.379     3    0.302    0.681
        15122.0   4.26   0.00  14.72   3.90  94.48  92.16     22    0.379     3    0.302    0.681
        15123.0   4.26   0.00  14.72   3.90  94.48  92.16     22    0.379     3    0.302    0.681
        15124.0   4.26   0.00  14.72   3.90  94.48  92.16     22    0.379     3    0.302    0.681
        15125.0   4.26   0.00  14.72   3.90  94.48  92.16     22    0.379     3    0.302    0.681
// 元数据M区和CSS区数据占比偏高
// YGCT总时间0.379s，平均每次17ms
// FGCT总时间0.302s，平均每次100ms  
```

> jstat -gc -t 24963 1000 10

```
Timestamp        S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
        15169.6 35328.0 34304.0 1504.4  0.0   454656.0 76401.8  1572864.0   61284.2   80792.0 76329.9 9624.0 8869.7     22    0.379   3      0.3020.681
        15170.6 35328.0 34304.0 1504.4  0.0   454656.0 78263.5  1572864.0   61284.2   80792.0 76329.9 9624.0 8869.7     22    0.379   3      0.3020.681
        15171.6 35328.0 34304.0 1504.4  0.0   454656.0 78263.5  1572864.0   61284.2   80792.0 76329.9 9624.0 8869.7     22    0.379   3      0.3020.681
        15172.6 35328.0 34304.0 1504.4  0.0   454656.0 78263.5  1572864.0   61284.2   80792.0 76329.9 9624.0 8869.7     22    0.379   3      0.3020.681
        15173.6 35328.0 34304.0 1504.4  0.0   454656.0 78263.5  1572864.0   61284.2   80792.0 76329.9 9624.0 8869.7     22    0.379   3      0.3020.681
        15174.6 35328.0 34304.0 1504.4  0.0   454656.0 78263.5  1572864.0   61284.2   80792.0 76329.9 9624.0 8869.7     22    0.379   3      0.3020.681
        15175.6 35328.0 34304.0 1504.4  0.0   454656.0 79081.1  1572864.0   61284.2   80792.0 76329.9 9624.0 8869.7     22    0.379   3      0.3020.681
        15176.6 35328.0 34304.0 1504.4  0.0   454656.0 79081.1  1572864.0   61284.2   80792.0 76329.9 9624.0 8869.7     22    0.379   3      0.3020.681
        15177.6 35328.0 34304.0 1504.4  0.0   454656.0 79081.1  1572864.0   61284.2   80792.0 76329.9 9624.0 8869.7     22    0.379   3      0.3020.681
        15178.6 35328.0 34304.0 1504.4  0.0   454656.0 79081.1  1572864.0   61284.2   80792.0 76329.9 9624.0 8869.7     22    0.379   3      0.3020.681
```

jmap 

> jmap -heap 24963

```
JVM version is 25.281-b09

using thread-local object allocation.
Parallel GC with 8 thread(s)

Heap Configuration:
   MinHeapFreeRatio         = 0
   MaxHeapFreeRatio         = 100
   MaxHeapSize              = 2147483648 (2048.0MB)
   NewSize                  = 536870912 (512.0MB)
   MaxNewSize               = 536870912 (512.0MB)
   OldSize                  = 1610612736 (1536.0MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
PS Young Generation
Eden Space:
   capacity = 465567744 (444.0MB)
   used     = 88400480 (84.30526733398438MB)
   free     = 377167264 (359.6947326660156MB)
   18.987672822969454% used
From Space:
   capacity = 36175872 (34.5MB)
   used     = 1540512 (1.469146728515625MB)
   free     = 34635360 (33.030853271484375MB)
   4.258396314538044% used
To Space:
   capacity = 35127296 (33.5MB)
   used     = 0 (0.0MB)
   free     = 35127296 (33.5MB)
   0.0% used
PS Old Generation
   capacity = 1610612736 (1536.0MB)
   used     = 62755008 (59.84783935546875MB)
   free     = 1547857728 (1476.1521606445312MB)
   3.89634370803833% used

37069 interned Strings occupying 4166944 bytes.
```

> jmap -histo 24963

```
num     #instances         #bytes  class name
----------------------------------------------
   1:        281794       76510064  [C
   2:         16757       21694320  [B
   3:         15737       18886344  [I
   4:        254828        6115872  java.lang.String
   5:         71975        4621392  [Ljava.lang.Object;
   6:         46497        4091736  java.lang.reflect.Method
   7:         69015        3864840  java.util.stream.ReferencePipeline$Head
   8:         72549        2901960  java.util.LinkedHashMap$Entry
   9:         41885        2533736  [Ljava.util.HashMap$Node;
  10:         70569        2258208  java.util.concurrent.ConcurrentHashMap$Node
  11:         50944        1630208  java.util.HashMap$Node
  12:         14486        1609464  java.lang.Class
  13:         28661        1605016  java.util.LinkedHashMap
  14:         33063        1322520  io.prometheus.client.Collector$MetricFamilySamples$Sample
  15:         44249        1061976  java.util.ArrayList
  16:         49650        1055424  [Ljava.lang.Class;
  17:         41921        1006104  io.micrometer.prometheus.MicrometerCollector$Family
  18:         30430         973760  java.lang.ref.WeakReference
  19:         11466         825552  java.lang.reflect.Field
  20:         17099         820752  io.micrometer.core.instrument.distribution.HistogramSnapshot
  21:          9372         751040  [S
  22:         26932         729072  [Ljava.lang.String;
  23:         11331         725184  java.util.stream.ReferencePipeline$3
  24:         14992         719616  java.util.HashMap
  25:           389         661904  [Ljava.util.concurrent.ConcurrentHashMap$Node;
  26:         14316         572640  java.lang.ref.SoftReference
  27:         16470         566544  [Ljava.lang.reflect.Method;
  28:         17408         557056  java.util.stream.Collectors$CollectorImpl
  29:         17408         557056  java.util.stream.ReduceOps$3
  30:         17408         557056  java.util.stream.ReduceOps$3ReducingSink
```

jstack

> jstack -l 24963

```
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.281-b09 mixed mode):

"Attach Listener" #160 daemon prio=9 os_prio=0 tid=0x00007f0ee0001000 nid=0x11947 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
        - None

"Async-pool-4" #68 prio=5 os_prio=0 tid=0x00007f0e482d5000 nid=0x9164 waiting on condition [0x00007f0e65bce000]
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x00000000828934d8> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
        at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)

   Locked ownable synchronizers:
        - None
...

"Reference Handler" #2 daemon prio=10 os_prio=0 tid=0x00007f0f301d2000 nid=0x618f in Object.wait() [0x00007f0efbdfc000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
        - locked <0x0000000080138448> (a java.lang.ref.Reference$Lock)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

   Locked ownable synchronizers:
        - None

"VM Thread" os_prio=0 tid=0x00007f0f301c8000 nid=0x618e runnable

"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x00007f0f3001e800 nid=0x6186 runnable

"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x00007f0f30020800 nid=0x6187 runnable

"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x00007f0f30022800 nid=0x6188 runnable

"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x00007f0f30024000 nid=0x6189 runnable

"GC task thread#4 (ParallelGC)" os_prio=0 tid=0x00007f0f30026000 nid=0x618a runnable

"GC task thread#5 (ParallelGC)" os_prio=0 tid=0x00007f0f30028000 nid=0x618b runnable

"GC task thread#6 (ParallelGC)" os_prio=0 tid=0x00007f0f30029800 nid=0x618c runnable

"GC task thread#7 (ParallelGC)" os_prio=0 tid=0x00007f0f3002b800 nid=0x618d runnable

"VM Periodic Task Thread" os_prio=0 tid=0x00007f0f30220000 nid=0x6197 waiting on condition

JNI global references: 1563
```

### 作业5

用于进行启动参数测试程序代码如下，一分钟内随机分配内存：

> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/jvm/GCLogAnalysis.java

程序编译和执行目录准备

> 01jvm/java-cource/src/main/java/com/piercebn/javacource/jvm 
>
> javac -g GCLogAnalysis.java
>
> cd 01jvm/java-cource/src/main/java

启动命令及参数：

使用G1 GC启动，堆内存1g，简洁日志输出

> java -XX:+PrintGC -XX:+PrintGCDateStamps -Xmx1g -Xms1g -XX:+UseG1GC com.piercebn.javacource.jvm.GCLogAnalysis

```
正在执行...
2021-08-15T16:33:42.346-0800: [GC pause (G1 Evacuation Pause) (young) 67M->20M(1024M), 0.0076662 secs]
2021-08-15T16:33:42.365-0800: [GC pause (G1 Evacuation Pause) (young) 72M->33M(1024M), 0.0043928 secs]
2021-08-15T16:33:42.378-0800: [GC pause (G1 Evacuation Pause) (young) 86M->50M(1024M), 0.0073582 secs]
2021-08-15T16:33:42.423-0800: [GC pause (G1 Evacuation Pause) (young) 154M->87M(1024M), 0.0131813 secs]
2021-08-15T16:33:42.451-0800: [GC pause (G1 Evacuation Pause) (young) 176M->114M(1024M), 0.0086563 secs]
2021-08-15T16:33:42.487-0800: [GC pause (G1 Evacuation Pause) (young) 234M->153M(1024M), 0.0129940 secs]
2021-08-15T16:33:42.530-0800: [GC pause (G1 Evacuation Pause) (young) 298M->191M(1024M), 0.0158287 secs]
2021-08-15T16:33:42.584-0800: [GC pause (G1 Evacuation Pause) (young) 368M->240M(1024M), 0.0211340 secs]
2021-08-15T16:33:42.651-0800: [GC pause (G1 Evacuation Pause) (young) 462M->308M(1024M), 0.0226642 secs]
2021-08-15T16:33:42.789-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 699M->399M(1024M), 0.0379839 secs]
2021-08-15T16:33:42.827-0800: [GC concurrent-root-region-scan-start]
2021-08-15T16:33:42.827-0800: [GC concurrent-root-region-scan-end, 0.0001682 secs]
2021-08-15T16:33:42.827-0800: [GC concurrent-mark-start]
2021-08-15T16:33:42.829-0800: [GC concurrent-mark-end, 0.0021414 secs]
2021-08-15T16:33:42.829-0800: [GC remark, 0.0008898 secs]
2021-08-15T16:33:42.830-0800: [GC cleanup 417M->407M(1024M), 0.0005415 secs]
2021-08-15T16:33:42.831-0800: [GC concurrent-cleanup-start]
2021-08-15T16:33:42.831-0800: [GC concurrent-cleanup-end, 0.0000211 secs]
2021-08-15T16:33:42.866-0800: [GC pause (G1 Evacuation Pause) (young) 658M->453M(1024M), 0.0180357 secs]
2021-08-15T16:33:42.887-0800: [GC pause (G1 Evacuation Pause) (mixed) 473M->401M(1024M), 0.0051159 secs]
2021-08-15T16:33:42.896-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 431M->412M(1024M), 0.0015316 secs]
2021-08-15T16:33:42.897-0800: [GC concurrent-root-region-scan-start]
2021-08-15T16:33:42.898-0800: [GC concurrent-root-region-scan-end, 0.0000951 secs]
2021-08-15T16:33:42.898-0800: [GC concurrent-mark-start]
2021-08-15T16:33:42.899-0800: [GC concurrent-mark-end, 0.0009781 secs]
2021-08-15T16:33:42.899-0800: [GC remark, 0.0010024 secs]
2021-08-15T16:33:42.900-0800: [GC cleanup 421M->417M(1024M), 0.0007243 secs]
2021-08-15T16:33:42.901-0800: [GC concurrent-cleanup-start]
2021-08-15T16:33:42.901-0800: [GC concurrent-cleanup-end, 0.0000162 secs]
2021-08-15T16:33:42.977-0800: [GC pause (G1 Evacuation Pause) (young)-- 828M->556M(1024M), 0.0141780 secs]
2021-08-15T16:33:42.993-0800: [GC pause (G1 Evacuation Pause) (mixed) 566M->508M(1024M), 0.0073161 secs]
2021-08-15T16:33:43.001-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 511M->509M(1024M), 0.0021129 secs]
2021-08-15T16:33:43.003-0800: [GC concurrent-root-region-scan-start]
2021-08-15T16:33:43.003-0800: [GC concurrent-root-region-scan-end, 0.0001167 secs]
2021-08-15T16:33:43.003-0800: [GC concurrent-mark-start]
2021-08-15T16:33:43.005-0800: [GC concurrent-mark-end, 0.0012698 secs]
2021-08-15T16:33:43.005-0800: [GC remark, 0.0020260 secs]
2021-08-15T16:33:43.007-0800: [GC cleanup 519M->511M(1024M), 0.0008847 secs]
2021-08-15T16:33:43.008-0800: [GC concurrent-cleanup-start]
2021-08-15T16:33:43.008-0800: [GC concurrent-cleanup-end, 0.0000298 secs]
2021-08-15T16:33:43.064-0800: [GC pause (G1 Evacuation Pause) (young) 839M->573M(1024M), 0.0080219 secs]
2021-08-15T16:33:43.075-0800: [GC pause (G1 Evacuation Pause) (mixed) 592M->489M(1024M), 0.0055103 secs]
2021-08-15T16:33:43.089-0800: [GC pause (G1 Evacuation Pause) (mixed) 540M->463M(1024M), 0.0037312 secs]
2021-08-15T16:33:43.093-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 463M->463M(1024M), 0.0018491 secs]
2021-08-15T16:33:43.095-0800: [GC concurrent-root-region-scan-start]
2021-08-15T16:33:43.095-0800: [GC concurrent-root-region-scan-end, 0.0000972 secs]
2021-08-15T16:33:43.095-0800: [GC concurrent-mark-start]
2021-08-15T16:33:43.097-0800: [GC concurrent-mark-end, 0.0011452 secs]
2021-08-15T16:33:43.097-0800: [GC remark, 0.0020591 secs]
2021-08-15T16:33:43.099-0800: [GC cleanup 470M->465M(1024M), 0.0009278 secs]
2021-08-15T16:33:43.100-0800: [GC concurrent-cleanup-start]
2021-08-15T16:33:43.100-0800: [GC concurrent-cleanup-end, 0.0000281 secs]
2021-08-15T16:33:43.159-0800: [GC pause (G1 Evacuation Pause) (young)-- 848M->619M(1024M), 0.0086153 secs]
2021-08-15T16:33:43.169-0800: [GC pause (G1 Evacuation Pause) (mixed) 632M->555M(1024M), 0.0060564 secs]
2021-08-15T16:33:43.176-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 558M->556M(1024M), 0.0021576 secs]
2021-08-15T16:33:43.178-0800: [GC concurrent-root-region-scan-start]
2021-08-15T16:33:43.178-0800: [GC concurrent-root-region-scan-end, 0.0001562 secs]
2021-08-15T16:33:43.178-0800: [GC concurrent-mark-start]
2021-08-15T16:33:43.179-0800: [GC concurrent-mark-end, 0.0010520 secs]
2021-08-15T16:33:43.179-0800: [GC remark, 0.0019018 secs]
2021-08-15T16:33:43.181-0800: [GC cleanup 563M->557M(1024M), 0.0006927 secs]
2021-08-15T16:33:43.182-0800: [GC concurrent-cleanup-start]
2021-08-15T16:33:43.182-0800: [GC concurrent-cleanup-end, 0.0000158 secs]
2021-08-15T16:33:43.228-0800: [GC pause (G1 Evacuation Pause) (young)-- 855M->653M(1024M), 0.0063282 secs]
2021-08-15T16:33:43.237-0800: [GC pause (G1 Evacuation Pause) (mixed) 676M->568M(1024M), 0.0044403 secs]
2021-08-15T16:33:43.248-0800: [GC pause (G1 Evacuation Pause) (mixed) 623M->524M(1024M), 0.0047568 secs]
2021-08-15T16:33:43.253-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 525M->523M(1024M), 0.0016238 secs]
2021-08-15T16:33:43.255-0800: [GC concurrent-root-region-scan-start]
2021-08-15T16:33:43.255-0800: [GC concurrent-root-region-scan-end, 0.0000812 secs]
2021-08-15T16:33:43.255-0800: [GC concurrent-mark-start]
2021-08-15T16:33:43.256-0800: [GC concurrent-mark-end, 0.0010587 secs]
2021-08-15T16:33:43.256-0800: [GC remark, 0.0018558 secs]
2021-08-15T16:33:43.258-0800: [GC cleanup 533M->526M(1024M), 0.0006594 secs]
2021-08-15T16:33:43.259-0800: [GC concurrent-cleanup-start]
2021-08-15T16:33:43.259-0800: [GC concurrent-cleanup-end, 0.0000188 secs]
执行结束!共生成对象次数:12958
```

使用G1 GC启动，堆内存4g，只对young区做了垃圾回收，简洁日志输出

> java -XX:+PrintGC -XX:+PrintGCDateStamps -Xmx4g -Xms4g -XX:+UseG1GC com.piercebn.javacource.jvm.GCLogAnalysis

```
正在执行...
2021-08-15T16:52:10.420-0800: [GC pause (G1 Evacuation Pause) (young) 204M->67M(4096M), 0.0277622 secs]
2021-08-15T16:52:10.479-0800: [GC pause (G1 Evacuation Pause) (young) 245M->123M(4096M), 0.0214723 secs]
2021-08-15T16:52:10.525-0800: [GC pause (G1 Evacuation Pause) (young) 301M->184M(4096M), 0.0255665 secs]
2021-08-15T16:52:10.574-0800: [GC pause (G1 Evacuation Pause) (young) 362M->235M(4096M), 0.0216321 secs]
2021-08-15T16:52:10.619-0800: [GC pause (G1 Evacuation Pause) (young) 413M->289M(4096M), 0.0243965 secs]
2021-08-15T16:52:10.665-0800: [GC pause (G1 Evacuation Pause) (young) 467M->340M(4096M), 0.0221442 secs]
2021-08-15T16:52:10.710-0800: [GC pause (G1 Evacuation Pause) (young) 518M->395M(4096M), 0.0224918 secs]
2021-08-15T16:52:10.757-0800: [GC pause (G1 Evacuation Pause) (young) 597M->461M(4096M), 0.0759884 secs]
2021-08-15T16:52:10.859-0800: [GC pause (G1 Evacuation Pause) (young) 687M->525M(4096M), 0.0387597 secs]
执行结束!共生成对象次数:9260
```

使用G1 GC启动，堆内存256m，触发Full GC退化，最终出现OutOfMemoryError堆内存溢出，即256m堆内存放不下创建的那么多对象，简洁日志输出

> java -XX:+PrintGC -XX:+PrintGCDateStamps -Xmx256m -Xms256m -XX:+UseG1GC com.piercebn.javacource.jvm.GCLogAnalysis

```
...
2021-08-15T17:30:03.807-0800: [GC pause (G1 Evacuation Pause) (young) 211M->202M(256M), 0.0008355 secs]
2021-08-15T17:30:03.809-0800: [GC pause (G1 Evacuation Pause) (mixed)-- 212M->208M(256M), 0.0012882 secs]
2021-08-15T17:30:03.811-0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 209M->209M(256M), 0.0006396 secs]
2021-08-15T17:30:03.811-0800: [GC concurrent-root-region-scan-start]
2021-08-15T17:30:03.811-0800: [GC concurrent-root-region-scan-end, 0.0000300 secs]
2021-08-15T17:30:03.811-0800: [GC concurrent-mark-start]
2021-08-15T17:30:03.812-0800: [GC concurrent-mark-end, 0.0006069 secs]
2021-08-15T17:30:03.812-0800: [GC remark, 0.0008070 secs]
2021-08-15T17:30:03.813-0800: [GC cleanup 214M->214M(256M), 0.0003566 secs]
2021-08-15T17:30:03.814-0800: [GC pause (G1 Evacuation Pause) (young)-- 221M->219M(256M), 0.0010316 secs]
2021-08-15T17:30:03.816-0800: [GC pause (G1 Humongous Allocation) (mixed)-- 224M->224M(256M), 0.0012766 secs]
2021-08-15T17:30:03.817-0800: [GC pause (G1 Evacuation Pause) (young) (initial-mark) 224M->224M(256M), 0.0011527 secs]
2021-08-15T17:30:03.819-0800: [GC concurrent-root-region-scan-start]
2021-08-15T17:30:03.819-0800: [GC concurrent-root-region-scan-end, 0.0000087 secs]
2021-08-15T17:30:03.819-0800: [GC concurrent-mark-start]
2021-08-15T17:30:03.819-0800: [GC pause (G1 Evacuation Pause) (young) 224M->224M(256M), 0.0011613 secs]
2021-08-15T17:30:03.820-0800: [Full GC (Allocation Failure)  224M->184M(256M), 0.0189364 secs]
2021-08-15T17:30:03.839-0800: [GC concurrent-mark-abort]
...
2021-08-15T17:30:04.028-0800: [Full GC (Allocation Failure)  203M->203M(256M), 0.0023168 secs]
2021-08-15T17:30:04.030-0800: [Full GC (Allocation Failure)  203M->203M(256M), 0.0026705 secs]
2021-08-15T17:30:04.033-0800: [GC concurrent-mark-abort]
2021-08-15T17:30:04.033-0800: [GC pause (G1 Evacuation Pause) (young) 203M->203M(256M), 0.0006747 secs]
2021-08-15T17:30:04.034-0800: [GC pause (G1 Evacuation Pause) (young) (initial-mark) 203M->203M(256M), 0.0007135 secs]
2021-08-15T17:30:04.035-0800: [GC concurrent-root-region-scan-start]
2021-08-15T17:30:04.035-0800: [GC concurrent-root-region-scan-end, 0.0000114 secs]
2021-08-15T17:30:04.035-0800: [GC concurrent-mark-start]
2021-08-15T17:30:04.035-0800: [Full GC (Allocation Failure)  203M->272K(256M), 0.0022341 secs]
2021-08-15T17:30:04.037-0800: [GC concurrent-mark-abort]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at com.piercebn.javacource.jvm.GCLogAnalysis.generateGarbage(GCLogAnalysis.java:47)
	at com.piercebn.javacource.jvm.GCLogAnalysis.main(GCLogAnalysis.java:27)
```

