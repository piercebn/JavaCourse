# 第二周作业

## 作业题目

1. （选做）使用 [GCLogAnalysis.java](https://github.com/JavaCourse00/JavaCourseCodes/blob/main/02nio/GCLogAnalysis.java) 自己演练一遍 串行/并行/CMS/G1 的案例。
2. （选做）使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。
3. （选做）如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。
4. （必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结，提交到 GitHub。
5. （选做）运行课上的例子，以及 Netty 的例子，分析相关现象。
6. （必做）写一段代码，使用 HttpClient 或 OkHttp 访问 [http://localhost:8801](http://localhost:8801/) ，代码提交到 GitHub

## 作业完成说明

### 作业1

用于进行启动参数测试程序代码如下，一分钟内随机分配内存：

> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/jvm/GCLogAnalysis.java

程序编译和执行目录准备

> 01jvm/java-cource/src/main/java/com/piercebn/javacource/jvm 
>
> javac -g GCLogAnalysis.java
>
> cd 01jvm/java-cource/src/main/java

#### 串行GC启动

> 串行GC，堆内存1g，只产生Young GC，使用垃圾回收器：DefNew
>
> java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx1g -Xms1g -XX:+UseSerialGC com.piercebn.javacource.jvm.GCLogAnalysis

```
正在执行...
2021-08-15T17:56:18.028-0800: [GC (Allocation Failure) 2021-08-15T17:56:18.028-0800: [DefNew: 279616K->34944K(314560K), 0.0634246 secs] 279616K->88545K(1013632K), 0.0634669 secs] [Times: user=0.03 sys=0.03, real=0.07 secs] 
2021-08-15T17:56:18.146-0800: [GC (Allocation Failure) 2021-08-15T17:56:18.146-0800: [DefNew: 314560K->34943K(314560K), 0.0757207 secs] 368161K->168499K(1013632K), 0.0757626 secs] [Times: user=0.03 sys=0.03, real=0.08 secs] 
2021-08-15T17:56:18.253-0800: [GC (Allocation Failure) 2021-08-15T17:56:18.253-0800: [DefNew: 314559K->34942K(314560K), 0.0583824 secs] 448115K->253820K(1013632K), 0.0584178 secs] [Times: user=0.03 sys=0.03, real=0.06 secs] 
2021-08-15T17:56:18.341-0800: [GC (Allocation Failure) 2021-08-15T17:56:18.341-0800: [DefNew: 314558K->34942K(314560K), 0.0682130 secs] 533436K->328972K(1013632K), 0.0682472 secs] [Times: user=0.03 sys=0.02, real=0.07 secs] 
2021-08-15T17:56:18.438-0800: [GC (Allocation Failure) 2021-08-15T17:56:18.438-0800: [DefNew: 314558K->34943K(314560K), 0.0521916 secs] 608588K->405820K(1013632K), 0.0522254 secs] [Times: user=0.02 sys=0.02, real=0.06 secs] 
2021-08-15T17:56:18.518-0800: [GC (Allocation Failure) 2021-08-15T17:56:18.519-0800: [DefNew: 314559K->34943K(314560K), 0.0510080 secs] 685436K->488426K(1013632K), 0.0510686 secs] [Times: user=0.03 sys=0.02, real=0.06 secs] 
2021-08-15T17:56:18.598-0800: [GC (Allocation Failure) 2021-08-15T17:56:18.598-0800: [DefNew: 314559K->34944K(314560K), 0.0500573 secs] 768042K->568603K(1013632K), 0.0500919 secs] [Times: user=0.03 sys=0.02, real=0.05 secs] 
2021-08-15T17:56:18.677-0800: [GC (Allocation Failure) 2021-08-15T17:56:18.677-0800: [DefNew: 314560K->34942K(314560K), 0.0449964 secs] 848219K->643215K(1013632K), 0.0450303 secs] [Times: user=0.03 sys=0.02, real=0.05 secs] 
2021-08-15T17:56:18.751-0800: [GC (Allocation Failure) 2021-08-15T17:56:18.751-0800: [DefNew: 314558K->314558K(314560K), 0.0000260 secs]2021-08-15T17:56:18.751-0800: [Tenured: 608272K->365841K(699072K), 0.0637411 secs] 922831K->365841K(1013632K), [Metaspace: 2734K->2734K(1056768K)], 0.0638092 secs] [Times: user=0.06 sys=0.00, real=0.06 secs] 
执行结束!共生成对象次数:10198
Heap
 def new generation   total 314560K, used 197609K [0x0000000780000000, 0x0000000795550000, 0x0000000795550000)
  eden space 279616K,  70% used [0x0000000780000000, 0x000000078c0fa7f0, 0x0000000791110000)
  from space 34944K,   0% used [0x0000000791110000, 0x0000000791110000, 0x0000000793330000)
  to   space 34944K,   0% used [0x0000000793330000, 0x0000000793330000, 0x0000000795550000)
 tenured generation   total 699072K, used 365841K [0x0000000795550000, 0x00000007c0000000, 0x00000007c0000000)
   the space 699072K,  52% used [0x0000000795550000, 0x00000007aba94578, 0x00000007aba94600, 0x00000007c0000000)
 Metaspace       used 2741K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 296K, capacity 386K, committed 512K, reserved 1048576K
```

> 串行GC，堆内存4g，GC频率降低，单次GC时间增加
>
> java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx4g -Xms4g -XX:+UseSerialGC com.piercebn.javacource.jvm.GCLogAnalysis

```
正在执行...
2021-08-15T18:03:24.131-0800: [GC (Allocation Failure) 2021-08-15T18:03:24.131-0800: [DefNew: 1118528K->139776K(1258304K), 0.3743503 secs] 1118528K->243230K(4054528K), 0.3743969 secs] [Times: user=0.10 sys=0.07, real=0.37 secs] 
执行结束!共生成对象次数:4218
Heap
 def new generation   total 1258304K, used 173156K [0x00000006c0000000, 0x0000000715550000, 0x0000000715550000)
  eden space 1118528K,   2% used [0x00000006c0000000, 0x00000006c2099288, 0x0000000704450000)
  from space 139776K, 100% used [0x000000070ccd0000, 0x0000000715550000, 0x0000000715550000)
  to   space 139776K,   0% used [0x0000000704450000, 0x0000000704450000, 0x000000070ccd0000)
 tenured generation   total 2796224K, used 103454K [0x0000000715550000, 0x00000007c0000000, 0x00000007c0000000)
   the space 2796224K,   3% used [0x0000000715550000, 0x000000071ba57ad0, 0x000000071ba57c00, 0x00000007c0000000)
 Metaspace       used 2741K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 296K, capacity 386K, committed 512K, reserved 1048576K
```

> 串行GC，堆内存512m，GC频率增高，单次GC时间减少
>
> java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx512m -Xms512m -XX:+UseSerialGC com.piercebn.javacource.jvm.GCLogAnalysis

```
正在执行...
2021-08-15T18:08:12.199-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.199-0800: [DefNew: 139776K->17472K(157248K), 0.0728369 secs] 139776K->49282K(506816K), 0.0728792 secs] [Times: user=0.02 sys=0.01, real=0.08 secs] 
2021-08-15T18:08:12.295-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.295-0800: [DefNew: 157248K->17469K(157248K), 0.0564482 secs] 189058K->91285K(506816K), 0.0564883 secs] [Times: user=0.02 sys=0.02, real=0.06 secs] 
2021-08-15T18:08:12.369-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.369-0800: [DefNew: 157193K->17469K(157248K), 0.0456549 secs] 231008K->142186K(506816K), 0.0457308 secs] [Times: user=0.02 sys=0.01, real=0.05 secs] 
2021-08-15T18:08:12.435-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.435-0800: [DefNew: 157245K->17470K(157248K), 0.0473780 secs] 281962K->188714K(506816K), 0.0474216 secs] [Times: user=0.01 sys=0.02, real=0.05 secs] 
2021-08-15T18:08:12.499-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.499-0800: [DefNew: 157246K->17470K(157248K), 0.0320626 secs] 328490K->238129K(506816K), 0.0320968 secs] [Times: user=0.02 sys=0.01, real=0.04 secs] 
2021-08-15T18:08:12.547-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.547-0800: [DefNew: 157246K->17471K(157248K), 0.0325892 secs] 377905K->285710K(506816K), 0.0326236 secs] [Times: user=0.01 sys=0.01, real=0.04 secs] 
2021-08-15T18:08:12.596-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.596-0800: [DefNew: 157060K->17471K(157248K), 0.0293485 secs] 425299K->331006K(506816K), 0.0293891 secs] [Times: user=0.01 sys=0.01, real=0.03 secs] 
2021-08-15T18:08:12.642-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.642-0800: [DefNew: 157247K->157247K(157248K), 0.0000155 secs]2021-08-15T18:08:12.642-0800: [Tenured: 313534K->286598K(349568K), 0.0467420 secs] 470782K->286598K(506816K), [Metaspace: 2734K->2734K(1056768K)], 0.0468445 secs] [Times: user=0.05 sys=0.00, real=0.04 secs] 
2021-08-15T18:08:12.704-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.704-0800: [DefNew: 139776K->17471K(157248K), 0.0043657 secs] 426374K->329679K(506816K), 0.0044638 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:08:12.725-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.725-0800: [DefNew: 157247K->157247K(157248K), 0.0000515 secs]2021-08-15T18:08:12.725-0800: [Tenured: 312207K->312659K(349568K), 0.0341052 secs] 469455K->312659K(506816K), [Metaspace: 2734K->2734K(1056768K)], 0.0342042 secs] [Times: user=0.04 sys=0.00, real=0.03 secs] 
2021-08-15T18:08:12.775-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.775-0800: [DefNew: 139776K->139776K(157248K), 0.0000156 secs]2021-08-15T18:08:12.775-0800: [Tenured: 312659K->324773K(349568K), 0.0386603 secs] 452435K->324773K(506816K), [Metaspace: 2734K->2734K(1056768K)], 0.0387234 secs] [Times: user=0.03 sys=0.00, real=0.04 secs] 
2021-08-15T18:08:12.829-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.829-0800: [DefNew: 139776K->139776K(157248K), 0.0000189 secs]2021-08-15T18:08:12.829-0800: [Tenured: 324773K->318431K(349568K), 0.0355911 secs] 464549K->318431K(506816K), [Metaspace: 2734K->2734K(1056768K)], 0.0356713 secs] [Times: user=0.04 sys=0.00, real=0.04 secs] 
2021-08-15T18:08:12.880-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.880-0800: [DefNew: 139776K->139776K(157248K), 0.0000162 secs]2021-08-15T18:08:12.880-0800: [Tenured: 318431K->335179K(349568K), 0.0269008 secs] 458207K->335179K(506816K), [Metaspace: 2734K->2734K(1056768K)], 0.0269593 secs] [Times: user=0.02 sys=0.01, real=0.02 secs] 
2021-08-15T18:08:12.922-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.922-0800: [DefNew: 139776K->139776K(157248K), 0.0000158 secs]2021-08-15T18:08:12.922-0800: [Tenured: 335179K->334209K(349568K), 0.0302091 secs] 474955K->334209K(506816K), [Metaspace: 2734K->2734K(1056768K)], 0.0302813 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
2021-08-15T18:08:12.971-0800: [GC (Allocation Failure) 2021-08-15T18:08:12.971-0800: [DefNew: 139776K->139776K(157248K), 0.0000152 secs]2021-08-15T18:08:12.971-0800: [Tenured: 334209K->341997K(349568K), 0.0359968 secs] 473985K->341997K(506816K), [Metaspace: 2734K->2734K(1056768K)], 0.0360555 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
2021-08-15T18:08:13.024-0800: [GC (Allocation Failure) 2021-08-15T18:08:13.024-0800: [DefNew: 139776K->139776K(157248K), 0.0000464 secs]2021-08-15T18:08:13.024-0800: [Tenured: 341997K->328898K(349568K), 0.0348197 secs] 481773K->328898K(506816K), [Metaspace: 2734K->2734K(1056768K)], 0.0349479 secs] [Times: user=0.04 sys=0.00, real=0.03 secs] 
2021-08-15T18:08:13.077-0800: [GC (Allocation Failure) 2021-08-15T18:08:13.077-0800: [DefNew: 139776K->139776K(157248K), 0.0000154 secs]2021-08-15T18:08:13.077-0800: [Tenured: 328898K->349550K(349568K), 0.0247536 secs] 468674K->356820K(506816K), [Metaspace: 2734K->2734K(1056768K)], 0.0248230 secs] [Times: user=0.02 sys=0.00, real=0.03 secs] 
执行结束!共生成对象次数:9120
Heap
 def new generation   total 157248K, used 74762K [0x00000007a0000000, 0x00000007aaaa0000, 0x00000007aaaa0000)
  eden space 139776K,  53% used [0x00000007a0000000, 0x00000007a49028d8, 0x00000007a8880000)
  from space 17472K,   0% used [0x00000007a8880000, 0x00000007a8880000, 0x00000007a9990000)
  to   space 17472K,   0% used [0x00000007a9990000, 0x00000007a9990000, 0x00000007aaaa0000)
 tenured generation   total 349568K, used 349550K [0x00000007aaaa0000, 0x00000007c0000000, 0x00000007c0000000)
   the space 349568K,  99% used [0x00000007aaaa0000, 0x00000007bfffba10, 0x00000007bfffbc00, 0x00000007c0000000)
 Metaspace       used 2741K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 296K, capacity 386K, committed 512K, reserved 1048576K
```

> 串行GC，堆内存256m，GC频率进一步提升，单次GC时间进一步减少，触发Full GC，最终出现OutOfMemoryError堆内存溢出
>
> java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx256m -Xms256m -XX:+UseSerialGC com.piercebn.javacource.jvm.GCLogAnalysis

```
正在执行...
2021-08-15T18:11:17.837-0800: [GC (Allocation Failure) 2021-08-15T18:11:17.837-0800: [DefNew: 69701K->8703K(78656K), 0.0128121 secs] 69701K->22538K(253440K), 0.0128845 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2021-08-15T18:11:17.860-0800: [GC (Allocation Failure) 2021-08-15T18:11:17.860-0800: [DefNew: 78442K->8700K(78656K), 0.0212105 secs] 92277K->52170K(253440K), 0.0212759 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2021-08-15T18:11:17.892-0800: [GC (Allocation Failure) 2021-08-15T18:11:17.892-0800: [DefNew: 78609K->8699K(78656K), 0.0172100 secs] 122079K->78032K(253440K), 0.0173075 secs] [Times: user=0.01 sys=0.01, real=0.01 secs] 
2021-08-15T18:11:17.920-0800: [GC (Allocation Failure) 2021-08-15T18:11:17.920-0800: [DefNew: 78651K->8702K(78656K), 0.0191105 secs] 147984K->103292K(253440K), 0.0191820 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2021-08-15T18:11:17.950-0800: [GC (Allocation Failure) 2021-08-15T18:11:17.950-0800: [DefNew: 78318K->8703K(78656K), 0.0156703 secs] 172907K->125020K(253440K), 0.0157160 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:17.978-0800: [GC (Allocation Failure) 2021-08-15T18:11:17.978-0800: [DefNew: 78655K->8704K(78656K), 0.0183798 secs] 194972K->149551K(253440K), 0.0184507 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
2021-08-15T18:11:18.008-0800: [GC (Allocation Failure) 2021-08-15T18:11:18.008-0800: [DefNew: 78656K->8703K(78656K), 0.0184928 secs] 219503K->172937K(253440K), 0.0185846 secs] [Times: user=0.00 sys=0.01, real=0.02 secs] 
2021-08-15T18:11:18.036-0800: [GC (Allocation Failure) 2021-08-15T18:11:18.036-0800: [DefNew: 78655K->78655K(78656K), 0.0000490 secs]2021-08-15T18:11:18.036-0800: [Tenured: 164234K->164742K(174784K), 0.0240482 secs] 242889K->164742K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0241587 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
2021-08-15T18:11:18.072-0800: [GC (Allocation Failure) 2021-08-15T18:11:18.072-0800: [DefNew: 69811K->69811K(78656K), 0.0000251 secs]2021-08-15T18:11:18.072-0800: [Tenured: 164742K->174765K(174784K), 0.0241765 secs] 234554K->179257K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0242703 secs] [Times: user=0.02 sys=0.01, real=0.02 secs] 
2021-08-15T18:11:18.107-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.107-0800: [Tenured: 174765K->174493K(174784K), 0.0207681 secs] 252858K->188094K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0208320 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-08-15T18:11:18.136-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.136-0800: [Tenured: 174781K->174304K(174784K), 0.0226041 secs] 253340K->190071K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0226437 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-08-15T18:11:18.165-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.165-0800: [Tenured: 174304K->174749K(174784K), 0.0108639 secs] 252490K->211563K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0109051 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.181-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.181-0800: [Tenured: 174749K->174762K(174784K), 0.0158980 secs] 253013K->214584K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0159706 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.202-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.202-0800: [Tenured: 174762K->174774K(174784K), 0.0168766 secs] 253132K->220035K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0169538 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.224-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.224-0800: [Tenured: 174774K->174210K(174784K), 0.0228822 secs] 253352K->212962K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0230058 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-08-15T18:11:18.254-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.254-0800: [Tenured: 174511K->174511K(174784K), 0.0075270 secs] 253016K->222765K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0075840 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.266-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.266-0800: [Tenured: 174689K->174409K(174784K), 0.0082931 secs] 253337K->231401K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0083360 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.278-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.278-0800: [Tenured: 174772K->174333K(174784K), 0.0138967 secs] 253426K->233236K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0139405 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
2021-08-15T18:11:18.294-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.294-0800: [Tenured: 174759K->174627K(174784K), 0.0294410 secs] 253413K->226980K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0295187 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
2021-08-15T18:11:18.328-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.328-0800: [Tenured: 174723K->174723K(174784K), 0.0064078 secs] 253333K->232728K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0064879 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.338-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.338-0800: [Tenured: 174723K->174723K(174784K), 0.0074868 secs] 253301K->237242K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0075282 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.348-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.348-0800: [Tenured: 174723K->174723K(174784K), 0.0076757 secs] 253318K->245325K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0077498 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.357-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.357-0800: [Tenured: 174723K->174760K(174784K), 0.0246723 secs] 253320K->238356K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0247133 secs] [Times: user=0.02 sys=0.00, real=0.03 secs] 
2021-08-15T18:11:18.384-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.384-0800: [Tenured: 174760K->174760K(174784K), 0.0089676 secs] 253404K->243707K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0090115 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.394-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.394-0800: [Tenured: 174760K->174760K(174784K), 0.0075052 secs] 253166K->245988K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0075358 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.403-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.403-0800: [Tenured: 174760K->174760K(174784K), 0.0017451 secs] 253238K->248621K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0017758 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.405-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.405-0800: [Tenured: 174760K->174740K(174784K), 0.0242266 secs] 253391K->241795K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0242613 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
2021-08-15T18:11:18.432-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.432-0800: [Tenured: 174740K->174740K(174784K), 0.0047075 secs] 253351K->244024K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0047939 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.438-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.438-0800: [Tenured: 174740K->174740K(174784K), 0.0048374 secs] 253330K->247751K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0049063 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.444-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.444-0800: [Tenured: 174740K->174740K(174784K), 0.0069818 secs] 253331K->248835K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0070484 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.452-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.452-0800: [Tenured: 174740K->174171K(174784K), 0.0254472 secs] 253276K->245192K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0255141 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-08-15T18:11:18.479-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.479-0800: [Tenured: 174779K->174779K(174784K), 0.0017788 secs] 253426K->247837K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0018889 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.482-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.482-0800: [Tenured: 174779K->174779K(174784K), 0.0088077 secs] 253427K->249245K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0088444 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.491-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.492-0800: [Tenured: 174779K->174779K(174784K), 0.0022396 secs] 253199K->249665K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0022718 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.494-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.494-0800: [Tenured: 174779K->174559K(174784K), 0.0251156 secs] 253414K->246356K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0251805 secs] [Times: user=0.02 sys=0.00, real=0.03 secs] 
2021-08-15T18:11:18.521-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.521-0800: [Tenured: 174559K->174559K(174784K), 0.0086326 secs] 252828K->248737K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0086741 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.530-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.531-0800: [Tenured: 174559K->174559K(174784K), 0.0075815 secs] 253077K->249380K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0076334 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.539-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.539-0800: [Tenured: 174559K->174559K(174784K), 0.0055364 secs] 252899K->250983K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0056070 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.545-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.545-0800: [Tenured: 174744K->174455K(174784K), 0.0219941 secs] 253368K->250013K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0220305 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-08-15T18:11:18.568-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.568-0800: [Tenured: 174743K->174743K(174784K), 0.0083811 secs] 253360K->251183K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0084477 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.577-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.577-0800: [Tenured: 174761K->174761K(174784K), 0.0045214 secs] 253405K->251970K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0046214 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.582-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.582-0800: [Tenured: 174761K->174761K(174784K), 0.0014204 secs] 253011K->252095K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0014730 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.584-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.584-0800: [Tenured: 174761K->174517K(174784K), 0.0231548 secs] 253104K->251830K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0232183 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-08-15T18:11:18.608-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.608-0800: [Tenured: 174517K->174517K(174784K), 0.0078902 secs] 253119K->252400K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0079530 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.616-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.616-0800: [Tenured: 174517K->174517K(174784K), 0.0018593 secs] 252871K->252256K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0018937 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.618-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.618-0800: [Tenured: 174517K->174710K(174784K), 0.0226968 secs] 252256K->252046K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0227299 secs] [Times: user=0.02 sys=0.00, real=0.03 secs] 
2021-08-15T18:11:18.641-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.641-0800: [Tenured: 174710K->174781K(174784K), 0.0167071 secs] 253295K->252875K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0167427 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.658-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.658-0800: [Tenured: 174781K->174781K(174784K), 0.0022856 secs] 253236K->252875K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0023187 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.660-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.660-0800: [Tenured: 174781K->174781K(174784K), 0.0015009 secs] 253268K->253191K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0015309 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.662-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.662-0800: [Tenured: 174781K->174781K(174784K), 0.0014663 secs] 253353K->253263K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0014900 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.664-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.664-0800: [Tenured: 174781K->174656K(174784K), 0.0024867 secs] 253396K->253078K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0025336 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.666-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.666-0800: [Tenured: 174728K->174728K(174784K), 0.0021833 secs] 253268K->253150K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0022152 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.669-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.669-0800: [Tenured: 174728K->174728K(174784K), 0.0014588 secs] 253304K->253294K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0014860 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-08-15T18:11:18.670-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.670-0800: [Tenured: 174728K->174656K(174784K), 0.0013171 secs] 253294K->253222K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0013395 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.672-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.672-0800: [Tenured: 174733K->174733K(174784K), 0.0014312 secs] 253374K->253191K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0014597 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.673-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.673-0800: [Tenured: 174733K->174733K(174784K), 0.0020299 secs] 253263K->253227K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0020853 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:11:18.676-0800: [Full GC (Allocation Failure) 2021-08-15T18:11:18.676-0800: [Tenured: 174733K->174733K(174784K), 0.0014777 secs] 253227K->253227K(253440K), [Metaspace: 2734K->2734K(1056768K)], 0.0015028 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3332)
	at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:124)
	at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:674)
	at java.lang.StringBuilder.append(StringBuilder.java:208)
	at com.piercebn.javacource.jvm.GCLogAnalysis.generateGarbage(GCLogAnalysis.java:58)
	at com.piercebn.javacource.jvm.GCLogAnalysis.main(GCLogAnalysis.java:27)
Heap
 def new generation   total 78656K, used 78585K [0x00000007b0000000, 0x00000007b5550000, 0x00000007b5550000)
  eden space 69952K, 100% used [0x00000007b0000000, 0x00000007b4450000, 0x00000007b4450000)
  from space 8704K,  99% used [0x00000007b4cd0000, 0x00000007b553e590, 0x00000007b5550000)
  to   space 8704K,   0% used [0x00000007b4450000, 0x00000007b4450000, 0x00000007b4cd0000)
 tenured generation   total 174784K, used 174733K [0x00000007b5550000, 0x00000007c0000000, 0x00000007c0000000)
   the space 174784K,  99% used [0x00000007b5550000, 0x00000007bfff3690, 0x00000007bfff3800, 0x00000007c0000000)
 Metaspace       used 2765K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 299K, capacity 386K, committed 512K, reserved 1048576K
```

#### 并行GC启动

> 并行GC，堆内存1g，只产生Young GC，使用垃圾回收器：PSYoungGen
>
> java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx1g -Xms1g -XX:+UseParallelGC com.piercebn.javacource.jvm.GCLogAnalysis

```
正在执行...
2021-08-15T18:24:13.313-0800: [GC (Allocation Failure) [PSYoungGen: 262144K->43514K(305664K)] 262144K->73327K(1005056K), 0.0668473 secs] [Times: user=0.05 sys=0.08, real=0.07 secs] 
2021-08-15T18:24:13.426-0800: [GC (Allocation Failure) [PSYoungGen: 305658K->43513K(305664K)] 335471K->156112K(1005056K), 0.1252926 secs] [Times: user=0.06 sys=0.17, real=0.13 secs] 
2021-08-15T18:24:13.579-0800: [GC (Allocation Failure) [PSYoungGen: 305657K->43516K(305664K)] 418256K->241036K(1005056K), 0.1009624 secs] [Times: user=0.05 sys=0.10, real=0.11 secs] 
2021-08-15T18:24:13.707-0800: [GC (Allocation Failure) [PSYoungGen: 305378K->43519K(305664K)] 502898K->318827K(1005056K), 0.0358795 secs] [Times: user=0.05 sys=0.11, real=0.04 secs] 
2021-08-15T18:24:13.770-0800: [GC (Allocation Failure) [PSYoungGen: 305618K->43508K(305664K)] 580926K->387970K(1005056K), 0.0369402 secs] [Times: user=0.05 sys=0.11, real=0.03 secs] 
2021-08-15T18:24:13.836-0800: [GC (Allocation Failure) [PSYoungGen: 305652K->43503K(160256K)] 650114K->458876K(859648K), 0.0523274 secs] [Times: user=0.06 sys=0.11, real=0.05 secs] 
2021-08-15T18:24:13.905-0800: [GC (Allocation Failure) [PSYoungGen: 160210K->71019K(232960K)] 575583K->493549K(932352K), 0.0071748 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
2021-08-15T18:24:13.926-0800: [GC (Allocation Failure) [PSYoungGen: 187755K->90288K(232960K)] 610285K->522001K(932352K), 0.0088612 secs] [Times: user=0.04 sys=0.01, real=0.01 secs] 
2021-08-15T18:24:13.949-0800: [GC (Allocation Failure) [PSYoungGen: 207024K->102343K(232960K)] 638737K->550856K(932352K), 0.0148412 secs] [Times: user=0.04 sys=0.02, real=0.02 secs] 
2021-08-15T18:24:13.983-0800: [GC (Allocation Failure) [PSYoungGen: 219079K->70221K(232960K)] 667592K->577893K(932352K), 0.0509593 secs] [Times: user=0.04 sys=0.06, real=0.05 secs] 
2021-08-15T18:24:14.048-0800: [GC (Allocation Failure) [PSYoungGen: 186775K->41417K(232960K)] 694447K->612615K(932352K), 0.0629794 secs] [Times: user=0.05 sys=0.08, real=0.07 secs] 
执行结束!共生成对象次数:8125
Heap
 PSYoungGen      total 232960K, used 46453K [0x00000007aab00000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 116736K, 4% used [0x00000007aab00000,0x00000007aafeb028,0x00000007b1d00000)
  from space 116224K, 35% used [0x00000007b1d00000,0x00000007b45726a0,0x00000007b8e80000)
  to   space 116224K, 0% used [0x00000007b8e80000,0x00000007b8e80000,0x00000007c0000000)
 ParOldGen       total 699392K, used 571198K [0x0000000780000000, 0x00000007aab00000, 0x00000007aab00000)
  object space 699392K, 81% used [0x0000000780000000,0x00000007a2dcf838,0x00000007aab00000)
 Metaspace       used 2741K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 296K, capacity 386K, committed 512K, reserved 1048576K
```

> 并行GC，堆内存512g，Young GC频率增加，使用垃圾回收器：PSYoungGen，触发Full GC，使用垃圾回收器：ParOldGen
>
> java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx512m -Xms512m -XX:+UseParallelGC com.piercebn.javacource.jvm.GCLogAnalysis

```
正在执行...
2021-08-15T18:28:16.888-0800: [GC (Allocation Failure) [PSYoungGen: 131577K->21492K(153088K)] 131577K->45685K(502784K), 0.0197631 secs] [Times: user=0.03 sys=0.09, real=0.02 secs] 
2021-08-15T18:28:16.927-0800: [GC (Allocation Failure) [PSYoungGen: 153076K->21502K(153088K)] 177269K->83181K(502784K), 0.0254233 secs] [Times: user=0.03 sys=0.12, real=0.03 secs] 
2021-08-15T18:28:16.969-0800: [GC (Allocation Failure) [PSYoungGen: 153086K->21499K(153088K)] 214765K->124547K(502784K), 0.0197234 secs] [Times: user=0.03 sys=0.08, real=0.02 secs] 
2021-08-15T18:28:17.004-0800: [GC (Allocation Failure) [PSYoungGen: 153083K->21500K(153088K)] 256131K->170401K(502784K), 0.0232725 secs] [Times: user=0.03 sys=0.09, real=0.02 secs] 
2021-08-15T18:28:17.045-0800: [GC (Allocation Failure) [PSYoungGen: 153084K->21503K(153088K)] 301985K->215435K(502784K), 0.0201439 secs] [Times: user=0.04 sys=0.08, real=0.02 secs] 
2021-08-15T18:28:17.082-0800: [GC (Allocation Failure) [PSYoungGen: 153085K->21502K(80384K)] 347017K->255453K(430080K), 0.0206001 secs] [Times: user=0.03 sys=0.08, real=0.02 secs] 
2021-08-15T18:28:17.110-0800: [GC (Allocation Failure) [PSYoungGen: 79741K->40520K(116736K)] 313692K->276961K(466432K), 0.0036911 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.122-0800: [GC (Allocation Failure) [PSYoungGen: 99400K->48226K(116736K)] 335841K->290522K(466432K), 0.0062009 secs] [Times: user=0.02 sys=0.01, real=0.00 secs] 
2021-08-15T18:28:17.137-0800: [GC (Allocation Failure) [PSYoungGen: 107106K->54793K(116736K)] 349402K->308028K(466432K), 0.0076683 secs] [Times: user=0.02 sys=0.02, real=0.01 secs] 
2021-08-15T18:28:17.155-0800: [GC (Allocation Failure) [PSYoungGen: 113452K->43144K(116736K)] 366687K->329153K(466432K), 0.0348223 secs] [Times: user=0.03 sys=0.03, real=0.04 secs] 
2021-08-15T18:28:17.199-0800: [GC (Allocation Failure) [PSYoungGen: 102024K->21474K(116736K)] 388033K->347242K(466432K), 0.0299407 secs] [Times: user=0.03 sys=0.05, real=0.03 secs] 
2021-08-15T18:28:17.231-0800: [Full GC (Ergonomics) [PSYoungGen: 21474K->0K(116736K)] [ParOldGen: 325767K->238530K(349696K)] 347242K->238530K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0241075 secs] [Times: user=0.12 sys=0.01, real=0.02 secs] 
2021-08-15T18:28:17.261-0800: [GC (Allocation Failure) [PSYoungGen: 58719K->22606K(116736K)] 297250K->261137K(466432K), 0.0025726 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.272-0800: [GC (Allocation Failure) [PSYoungGen: 81486K->15841K(116736K)] 320017K->275723K(466432K), 0.0024986 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.281-0800: [GC (Allocation Failure) [PSYoungGen: 74721K->25113K(116736K)] 334603K->299289K(466432K), 0.0026000 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.293-0800: [GC (Allocation Failure) [PSYoungGen: 83942K->20052K(116736K)] 358118K->318045K(466432K), 0.0041230 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.297-0800: [Full GC (Ergonomics) [PSYoungGen: 20052K->0K(116736K)] [ParOldGen: 297993K->271456K(349696K)] 318045K->271456K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0180959 secs] [Times: user=0.10 sys=0.00, real=0.02 secs] 
2021-08-15T18:28:17.322-0800: [GC (Allocation Failure) [PSYoungGen: 58880K->21169K(116736K)] 330336K->292626K(466432K), 0.0022276 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.332-0800: [GC (Allocation Failure) [PSYoungGen: 80049K->24401K(116736K)] 351506K->315309K(466432K), 0.0029812 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.342-0800: [GC (Allocation Failure) [PSYoungGen: 82784K->15862K(116736K)] 373692K->330809K(466432K), 0.0036757 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.346-0800: [Full GC (Ergonomics) [PSYoungGen: 15862K->0K(116736K)] [ParOldGen: 314947K->285761K(349696K)] 330809K->285761K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0217535 secs] [Times: user=0.13 sys=0.00, real=0.02 secs] 
2021-08-15T18:28:17.375-0800: [GC (Allocation Failure) [PSYoungGen: 58880K->20907K(116736K)] 344641K->306669K(466432K), 0.0016928 secs] [Times: user=0.01 sys=0.01, real=0.00 secs] 
2021-08-15T18:28:17.384-0800: [GC (Allocation Failure) [PSYoungGen: 79093K->20833K(116736K)] 364855K->327062K(466432K), 0.0050334 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.396-0800: [GC (Allocation Failure) [PSYoungGen: 79713K->20277K(116736K)] 385942K->346346K(466432K), 0.0028707 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.399-0800: [Full GC (Ergonomics) [PSYoungGen: 20277K->0K(116736K)] [ParOldGen: 326068K->302678K(349696K)] 346346K->302678K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0234389 secs] [Times: user=0.12 sys=0.00, real=0.03 secs] 
2021-08-15T18:28:17.431-0800: [GC (Allocation Failure) [PSYoungGen: 58847K->20699K(116736K)] 361526K->323378K(466432K), 0.0027167 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.444-0800: [GC (Allocation Failure) [PSYoungGen: 79579K->20073K(116736K)] 382258K->342065K(466432K), 0.0028911 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.447-0800: [Full GC (Ergonomics) [PSYoungGen: 20073K->0K(116736K)] [ParOldGen: 321991K->312479K(349696K)] 342065K->312479K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0232685 secs] [Times: user=0.11 sys=0.00, real=0.03 secs] 
2021-08-15T18:28:17.479-0800: [GC (Allocation Failure) [PSYoungGen: 58880K->23356K(116736K)] 371359K->335836K(466432K), 0.0026377 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2021-08-15T18:28:17.489-0800: [GC (Allocation Failure) [PSYoungGen: 81927K->20704K(116736K)] 394407K->355488K(466432K), 0.0083083 secs] [Times: user=0.02 sys=0.02, real=0.01 secs] 
2021-08-15T18:28:17.498-0800: [Full GC (Ergonomics) [PSYoungGen: 20704K->0K(116736K)] [ParOldGen: 334784K->313611K(349696K)] 355488K->313611K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0213686 secs] [Times: user=0.12 sys=0.00, real=0.02 secs] 
2021-08-15T18:28:17.527-0800: [GC (Allocation Failure) [PSYoungGen: 58880K->17553K(116736K)] 372491K->331164K(466432K), 0.0017191 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.537-0800: [GC (Allocation Failure) [PSYoungGen: 76433K->20676K(116736K)] 390044K->350713K(466432K), 0.0047971 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2021-08-15T18:28:17.542-0800: [Full GC (Ergonomics) [PSYoungGen: 20676K->0K(116736K)] [ParOldGen: 330037K->315147K(349696K)] 350713K->315147K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0194427 secs] [Times: user=0.12 sys=0.00, real=0.02 secs] 
2021-08-15T18:28:17.570-0800: [GC (Allocation Failure) [PSYoungGen: 58880K->17648K(116736K)] 374027K->332796K(466432K), 0.0023280 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.581-0800: [GC (Allocation Failure) [PSYoungGen: 76244K->40655K(116736K)] 391391K->355802K(466432K), 0.0028487 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.591-0800: [GC (Allocation Failure) --[PSYoungGen: 99535K->99535K(116736K)] 414682K->449218K(466432K), 0.0093705 secs] [Times: user=0.03 sys=0.03, real=0.01 secs] 
2021-08-15T18:28:17.600-0800: [Full GC (Ergonomics) [PSYoungGen: 99535K->0K(116736K)] [ParOldGen: 349683K->320368K(349696K)] 449218K->320368K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0236864 secs] [Times: user=0.12 sys=0.00, real=0.02 secs] 
2021-08-15T18:28:17.631-0800: [GC (Allocation Failure) [PSYoungGen: 58821K->17444K(116736K)] 379189K->337813K(466432K), 0.0016798 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.642-0800: [GC (Allocation Failure) [PSYoungGen: 76004K->18925K(116736K)] 396373K->354684K(466432K), 0.0035477 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.645-0800: [Full GC (Ergonomics) [PSYoungGen: 18925K->0K(116736K)] [ParOldGen: 335759K->310804K(349696K)] 354684K->310804K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0206122 secs] [Times: user=0.11 sys=0.00, real=0.02 secs] 
2021-08-15T18:28:17.672-0800: [GC (Allocation Failure) [PSYoungGen: 58874K->23693K(120832K)] 369679K->334498K(470528K), 0.0018446 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.683-0800: [GC (Allocation Failure) [PSYoungGen: 88205K->44657K(109568K)] 399010K->355461K(459264K), 0.0039704 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.696-0800: [GC (Allocation Failure) [PSYoungGen: 109143K->57846K(116736K)] 419948K->375925K(466432K), 0.0040832 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
2021-08-15T18:28:17.707-0800: [GC (Allocation Failure) [PSYoungGen: 116726K->56685K(116736K)] 434805K->392490K(466432K), 0.0060954 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
2021-08-15T18:28:17.713-0800: [Full GC (Ergonomics) [PSYoungGen: 56685K->0K(116736K)] [ParOldGen: 335804K->317830K(349696K)] 392490K->317830K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0213401 secs] [Times: user=0.11 sys=0.00, real=0.02 secs] 
2021-08-15T18:28:17.743-0800: [GC (Allocation Failure) [PSYoungGen: 58826K->17518K(116736K)] 376657K->335349K(466432K), 0.0015361 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.752-0800: [GC (Allocation Failure) [PSYoungGen: 76269K->17331K(116736K)] 394100K->351015K(466432K), 0.0025660 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.755-0800: [Full GC (Ergonomics) [PSYoungGen: 17331K->0K(116736K)] [ParOldGen: 333683K->316377K(349696K)] 351015K->316377K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0214519 secs] [Times: user=0.12 sys=0.00, real=0.02 secs] 
2021-08-15T18:28:17.785-0800: [GC (Allocation Failure) [PSYoungGen: 58723K->15875K(116736K)] 375100K->332253K(466432K), 0.0017005 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.795-0800: [GC (Allocation Failure) [PSYoungGen: 74755K->20623K(116736K)] 391133K->351978K(466432K), 0.0025667 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
2021-08-15T18:28:17.797-0800: [Full GC (Ergonomics) [PSYoungGen: 20623K->0K(116736K)] [ParOldGen: 331355K->318458K(349696K)] 351978K->318458K(466432K), [Metaspace: 2734K->2734K(1056768K)], 0.0221082 secs] [Times: user=0.11 sys=0.00, real=0.02 secs] 
执行结束!共生成对象次数:10809
Heap
 PSYoungGen      total 116736K, used 2963K [0x00000007b5580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 58880K, 5% used [0x00000007b5580000,0x00000007b5864fe0,0x00000007b8f00000)
  from space 57856K, 0% used [0x00000007b8f00000,0x00000007b8f00000,0x00000007bc780000)
  to   space 57856K, 0% used [0x00000007bc780000,0x00000007bc780000,0x00000007c0000000)
 ParOldGen       total 349696K, used 318458K [0x00000007a0000000, 0x00000007b5580000, 0x00000007b5580000)
  object space 349696K, 91% used [0x00000007a0000000,0x00000007b36feb80,0x00000007b5580000)
 Metaspace       used 2741K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 296K, capacity 386K, committed 512K, reserved 1048576K
```

#### CMS GC启动

> CMS GC，堆内存1g，Young GC，使用垃圾回收器：ParNew，Full GC，老年代使用垃圾回收器：CMS(concurrent mark-sweep)
>
> java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC com.piercebn.javacource.jvm.GCLogAnalysis

```
正在执行...
2021-08-15T18:32:44.297-0800: [GC (Allocation Failure) 2021-08-15T18:32:44.297-0800: [ParNew: 279616K->34944K(314560K), 0.0327596 secs] 279616K->86613K(1013632K), 0.0328143 secs] [Times: user=0.05 sys=0.16, real=0.03 secs] 
2021-08-15T18:32:44.374-0800: [GC (Allocation Failure) 2021-08-15T18:32:44.374-0800: [ParNew: 314560K->34944K(314560K), 0.0390928 secs] 366229K->164401K(1013632K), 0.0391677 secs] [Times: user=0.06 sys=0.15, real=0.04 secs] 
2021-08-15T18:32:44.445-0800: [GC (Allocation Failure) 2021-08-15T18:32:44.445-0800: [ParNew: 314560K->34940K(314560K), 0.0452294 secs] 444017K->243348K(1013632K), 0.0452646 secs] [Times: user=0.32 sys=0.03, real=0.05 secs] 
2021-08-15T18:32:44.519-0800: [GC (Allocation Failure) 2021-08-15T18:32:44.519-0800: [ParNew: 314556K->34944K(314560K), 0.0486469 secs] 522964K->327940K(1013632K), 0.0486841 secs] [Times: user=0.34 sys=0.03, real=0.05 secs] 
2021-08-15T18:32:44.597-0800: [GC (Allocation Failure) 2021-08-15T18:32:44.597-0800: [ParNew: 314560K->34943K(314560K), 0.0545088 secs] 607556K->411048K(1013632K), 0.0545788 secs] [Times: user=0.32 sys=0.03, real=0.06 secs] 
2021-08-15T18:32:44.651-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 376105K(699072K)] 417002K(1013632K), 0.0003765 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:32:44.652-0800: [CMS-concurrent-mark-start]
2021-08-15T18:32:44.655-0800: [CMS-concurrent-mark: 0.003/0.003 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:32:44.655-0800: [CMS-concurrent-preclean-start]
2021-08-15T18:32:44.656-0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:32:44.656-0800: [CMS-concurrent-abortable-preclean-start]
2021-08-15T18:32:44.682-0800: [GC (Allocation Failure) 2021-08-15T18:32:44.682-0800: [ParNew: 314559K->34943K(314560K), 0.0468938 secs] 690664K->486488K(1013632K), 0.0469310 secs] [Times: user=0.31 sys=0.03, real=0.04 secs] 
2021-08-15T18:32:44.759-0800: [GC (Allocation Failure) 2021-08-15T18:32:44.759-0800: [ParNew: 314369K->34943K(314560K), 0.0518937 secs] 765914K->572252K(1013632K), 0.0519291 secs] [Times: user=0.34 sys=0.03, real=0.06 secs] 
2021-08-15T18:32:44.844-0800: [GC (Allocation Failure) 2021-08-15T18:32:44.844-0800: [ParNew: 314559K->34943K(314560K), 0.0519298 secs] 851868K->658620K(1013632K), 0.0519647 secs] [Times: user=0.33 sys=0.04, real=0.05 secs] 
2021-08-15T18:32:44.926-0800: [GC (Allocation Failure) 2021-08-15T18:32:44.926-0800: [ParNew: 314559K->314559K(314560K), 0.0000761 secs]2021-08-15T18:32:44.926-0800: [CMS2021-08-15T18:32:44.926-0800: [CMS-concurrent-abortable-preclean: 0.008/0.270 secs] [Times: user=1.10 sys=0.10, real=0.27 secs] 
 (concurrent mode failure): 623677K->345740K(699072K), 0.0471742 secs] 938236K->345740K(1013632K), [Metaspace: 2734K->2734K(1056768K)], 0.0473287 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
2021-08-15T18:32:45.004-0800: [GC (Allocation Failure) 2021-08-15T18:32:45.004-0800: [ParNew: 279616K->34943K(314560K), 0.0096644 secs] 625356K->432259K(1013632K), 0.0097011 secs] [Times: user=0.06 sys=0.00, real=0.01 secs] 
2021-08-15T18:32:45.013-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 397315K(699072K)] 438282K(1013632K), 0.0001412 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:32:45.014-0800: [CMS-concurrent-mark-start]
2021-08-15T18:32:45.015-0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-08-15T18:32:45.015-0800: [CMS-concurrent-preclean-start]
2021-08-15T18:32:45.016-0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-08-15T18:32:45.016-0800: [CMS-concurrent-abortable-preclean-start]
2021-08-15T18:32:45.050-0800: [GC (Allocation Failure) 2021-08-15T18:32:45.050-0800: [ParNew: 314559K->34943K(314560K), 0.0121239 secs] 711875K->520400K(1013632K), 0.0121890 secs] [Times: user=0.07 sys=0.00, real=0.01 secs] 
2021-08-15T18:32:45.096-0800: [GC (Allocation Failure) 2021-08-15T18:32:45.096-0800: [ParNew: 314559K->34943K(314560K), 0.0108446 secs] 800016K->600340K(1013632K), 0.0108830 secs] [Times: user=0.06 sys=0.00, real=0.01 secs] 
2021-08-15T18:32:45.138-0800: [GC (Allocation Failure) 2021-08-15T18:32:45.138-0800: [ParNew: 314559K->34943K(314560K), 0.0190876 secs] 879956K->677928K(1013632K), 0.0191306 secs] [Times: user=0.12 sys=0.01, real=0.02 secs] 
执行结束!共生成对象次数:13726
Heap
 par new generation   total 314560K, used 46181K [0x0000000780000000, 0x0000000795550000, 0x0000000795550000)
  eden space 279616K,   4% used [0x0000000780000000, 0x0000000780af9a68, 0x0000000791110000)
  from space 34944K,  99% used [0x0000000791110000, 0x000000079332fcd8, 0x0000000793330000)
  to   space 34944K,   0% used [0x0000000793330000, 0x0000000793330000, 0x0000000795550000)
 concurrent mark-sweep generation total 699072K, used 642985K [0x0000000795550000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 2741K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 296K, capacity 386K, committed 512K, reserved 1048576K
```

#### C1 GC启动

> G1 GC，堆内存1g，简洁日志输出
>
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

> G1 GC启动，堆内存4g，只对young区做了垃圾回收，简洁日志输出
>
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

> G1 GC启动，堆内存256m，触发Full GC退化，最终出现OutOfMemoryError堆内存溢出，即256m堆内存放不下创建的那么多对象，简洁日志输出
>
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

### 作业2

压测工具准备

> 使用压测工具wrk（基于Mac安装）
>
> brew update
>
> brew install wrk

演练程序

> https://github.com/piercebn/JavaCourse/blob/main/01jvm/gateway-server-0.0.1-SNAPSHOT.jar

演练执行

> cd 01jvm
>
> java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseSerialGC  -jar gateway-server-0.0.1-SNAPSHOT.jar
>
> java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseParallelGC -jar gateway-server-0.0.1-SNAPSHOT.jar
>
> java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseConcMarkSweepGC  -jar gateway-server-0.0.1-SNAPSHOT.jar
>
> java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseG1GC -XX:MaxGCPauseMillis=50  -jar gateway-server-0.0.1-SNAPSHOT.jar
>
> wrk -c 40 -d30s --latency http://localhost:8088/api/hello
>
> jps -l
>
> jstat -gcutil -t pid 1000 60

```
Running 30s test @ http://localhost:8088/api/hello
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.92ms    5.78ms 144.39ms   95.93%
    Req/Sec    18.88k     8.27k   35.42k    68.50%
  Latency Distribution
     50%  720.00us
     75%    1.14ms
     90%    2.89ms
     99%   27.95ms
  1127523 requests in 30.03s, 134.62MB read
Requests/sec:  37548.04
Transfer/sec:      4.48MB

UseParallelGC
Timestamp         S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
          661.1   0.00   0.00  12.11   2.48  95.47  93.58      4    0.039     2    0.085    0.124
          662.1   0.00   0.00  12.11   2.48  95.47  93.58      4    0.039     2    0.085    0.124
          663.1   0.00   0.00  12.11   2.48  95.47  93.58      4    0.039     2    0.085    0.124
          664.1   0.00   0.00  12.11   2.48  95.47  93.58      4    0.039     2    0.085    0.124
          665.1   0.00   0.00  12.11   2.48  95.47  93.58      4    0.039     2    0.085    0.124
          666.1   0.00   0.00  30.11   2.48  95.47  93.58      4    0.039     2    0.085    0.124
          667.2   0.00  13.91  31.12   2.48  95.05  91.53      5    0.043     2    0.085    0.128
          668.1  28.81   0.00  28.09   2.48  95.26  91.55      6    0.058     2    0.085    0.143
          669.2   0.00  28.21  33.50   2.49  94.90  91.55      7    0.061     2    0.085    0.146
          670.1  28.21   0.00  23.76   2.49  95.27  91.55      8    0.070     2    0.085    0.155
          671.2   0.00  50.00  32.48   3.19  95.35  91.55      9    0.079     2    0.085    0.164
          672.1   0.00   1.09  27.98   3.20  95.39  91.55     11    0.093     2    0.085    0.178
          673.2   0.00   1.25  41.14   3.21  94.85  91.55     13    0.096     2    0.085    0.181
          674.2   0.00   1.12  32.25   3.21  94.85  91.55     15    0.099     2    0.085    0.184
          675.1   0.00   1.22  70.79   3.21  94.85  91.55     17    0.102     2    0.085    0.187
          676.2   1.76   0.00  49.24   3.21  94.86  91.56     20    0.107     2    0.085    0.192
          677.2   0.00   2.08  21.08   3.21  94.86  91.56     23    0.112     2    0.085    0.197
          678.2   2.17   0.00  16.89   3.21  94.91  91.66     26    0.117     2    0.085    0.202
          679.2   2.78   0.00  11.02   3.21  94.91  91.66     30    0.123     2    0.085    0.209
          680.2   4.02   0.00   2.10   3.22  94.91  91.66     34    0.130     2    0.085    0.215
          681.2   0.00   4.55  93.00   3.23  94.91  91.66     37    0.135     2    0.085    0.220
          682.2   0.00   6.25  75.88   3.25  94.91  91.66     41    0.142     2    0.085    0.227
          683.2   0.00   9.38  54.84   3.25  94.91  91.66     45    0.148     2    0.085    0.233
          684.2   0.00  10.00  11.04   3.25  94.91  91.66     49    0.154     2    0.085    0.240
          685.2   0.00  12.50  27.49   3.25  94.92  91.68     53    0.160     2    0.085    0.245
          686.2  14.58   0.00  21.80   3.25  94.92  91.68     56    0.166     2    0.085    0.251
          687.2   0.00  14.58  75.19   3.25  94.92  91.68     59    0.171     2    0.085    0.256
          688.2   0.00  28.12   2.94   3.26  94.92  91.68     63    0.177     2    0.085    0.262
          689.2  28.12   0.00  45.95   3.26  94.92  91.68     66    0.182     2    0.085    0.267
          690.2  25.00   0.00   6.31   3.28  94.92  91.68     70    0.188     2    0.085    0.273
          691.2  50.00   0.00   0.00   3.28  94.92  91.68     74    0.194     2    0.085    0.279
          692.2   0.00  50.00  81.51   3.28  94.92  91.68     77    0.199     2    0.085    0.284
          693.2   0.00  50.00  60.38   3.29  94.92  91.68     81    0.205     2    0.085    0.290
          694.2   0.00  50.00  32.86   3.29  94.96  91.68     85    0.211     2    0.085    0.296
          695.2   0.00  50.00  15.00   3.29  94.96  91.68     89    0.217     2    0.085    0.302
          696.2   0.00  50.00  97.89   3.29  94.96  91.68     91    0.220     2    0.085    0.305
          697.2   0.00  50.00  97.89   3.29  94.96  91.68     91    0.220     2    0.085    0.305
          698.3   0.00  50.00  97.89   3.29  94.96  91.68     91    0.220     2    0.085    0.305
          699.3   0.00  50.00  97.89   3.29  94.96  91.68     91    0.220     2    0.085    0.305
          700.2   0.00  50.00  97.89   3.29  94.96  91.68     91    0.220     2    0.085    0.305

```



### 作业3

待演练



### 作业4

不同 GC 和堆内存的总结

- 串行GC，单线程，GC执行效率低，使用垃圾回收器：DefNew，会触发全线暂停(STW)
- 并行GC，多线程并行执行，年轻代，使用垃圾回收器：PSYoungGen，老年代，使用垃圾回收器：ParOldGen，会触发全线暂停(STW)
- CMS GC，与应用程序并发执行，年轻代，使用垃圾回收器：ParNew(串行化垃圾回收器进行升级改造变成多线程的并行执行)，老年代，使用垃圾回收器：CMS(concurrent mark-sweep)
- G1 GC，暂停时间短，但是，堆内存不足时，由于垃圾生成太快太多，垃圾回收不过来，老年代很快被占满，会触发Full GC退化，退化成串行化了，对性能影响非常大
- 随着堆内存配置的减小（从4g-256m），Young GC频率增加，单次GC时间减少；当堆内存配置减少到一定数值时，会触发Full GC，Full GC也会随着堆内存配置的减小执行的更加频繁；随着堆内存配置的进一步减小，当堆内存放不下创建的那么多对象时，最终会出现OutOfMemoryError堆内存溢出



### 作业5

#### BIO示例

BIO服务端程序示例

> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/HttpServer01.java （单线程）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/HttpServer02.java （多线程）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/HttpServer03.java （线程池）

BIO服务端程序执行

> wrk -c 40 -d30s --latency http://localhost:8801/
>
> wrk -c 40 -d30s --latency http://localhost:8802/
>
> wrk -c 40 -d30s --latency http://localhost:8803/

```java
Running 30s test @ http://localhost:8801/
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    15.53ms   36.51ms 438.60ms   90.37%
    Req/Sec   203.32    347.48     1.75k    86.85%
  Latency Distribution
     50%    2.19ms
     75%    3.85ms
     90%   51.29ms
     99%  189.58ms
  10389 requests in 30.03s, 1.35MB read
  Socket errors: connect 0, read 42443, write 1, timeout 0
Requests/sec:    345.97 // 单线程执行qps
Transfer/sec:     45.87KB

Running 30s test @ http://localhost:8802/
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    17.09ms   22.95ms 362.44ms   98.27%
    Req/Sec   184.84     93.24   530.00     63.28%
  Latency Distribution
     50%   14.40ms
     75%   22.44ms
     90%   27.86ms
     99%  135.04ms
  10401 requests in 30.10s, 1.35MB read
  Socket errors: connect 0, read 44399, write 1, timeout 0
Requests/sec:    345.54 // 多线程执行qps
Transfer/sec:     45.83KB
  
Running 30s test @ http://localhost:8803/
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    18.36ms   27.73ms 524.59ms   97.44%
    Req/Sec   196.11     92.71   564.00     73.05%
  Latency Distribution
     50%   14.17ms
     75%   24.18ms
     90%   33.04ms
     99%   90.54ms
  11321 requests in 30.09s, 1.26MB read
  Socket errors: connect 0, read 35093, write 1, timeout 0
Requests/sec:    376.29 // 线程池执行qps
Transfer/sec:     42.99KB
```

#### NIO示例

Netty服务端程序示例

> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/netty/NettyHttpServer.java

Netty服务端程序执行

> wrk -c 40 -d30s --latency http://localhost:8808/

```java
Running 30s test @ http://localhost:8808/
  2 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   594.63us    2.98ms 155.05ms   98.90%
    Req/Sec    54.45k     7.44k   59.39k    92.00%
  Latency Distribution
     50%  336.00us
     75%  352.00us
     90%  435.00us
     99%    4.81ms
  3249884 requests in 30.00s, 331.63MB read
Requests/sec: 108319.49 // qps相对BIO方式增强很多
Transfer/sec:     11.05MB
```



### 作业6

客户端程序示例（ 采用HttpClient方式访问http://localhost:8801/ ）

> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/HttpClient.java

```java
CloseableHttpClient httpclient = HttpClients.createDefault();
HttpGet httpGet = new HttpGet("http://localhost:8801/");
CloseableHttpResponse response = httpclient.execute(httpGet);
try {
  System.out.println("Status: "+response.getStatusLine());
  HttpEntity entity = response.getEntity();
  System.out.println("Result: "+EntityUtils.toString(entity));
} finally {
  response.close();
}
```

客户端程序执行结果

```java
21:52:37.258 [main] DEBUG org.apache.http.client.protocol.RequestAddCookies - CookieSpec selected: default
21:52:37.267 [main] DEBUG org.apache.http.client.protocol.RequestAuthCache - Auth cache not set in the context
21:52:37.268 [main] DEBUG org.apache.http.impl.conn.PoolingHttpClientConnectionManager - Connection request: [route: {}->http://localhost:8801][total available: 0; route allocated: 0 of 2; total allocated: 0 of 20]
21:52:37.282 [main] DEBUG org.apache.http.impl.conn.PoolingHttpClientConnectionManager - Connection leased: [id: 0][route: {}->http://localhost:8801][total available: 0; route allocated: 1 of 2; total allocated: 1 of 20]
21:52:37.283 [main] DEBUG org.apache.http.impl.execchain.MainClientExec - Opening connection {}->http://localhost:8801
21:52:37.288 [main] DEBUG org.apache.http.impl.conn.DefaultHttpClientConnectionOperator - Connecting to localhost/127.0.0.1:8801
21:52:37.289 [main] DEBUG org.apache.http.impl.conn.DefaultHttpClientConnectionOperator - Connection established 127.0.0.1:49757<->127.0.0.1:8801
21:52:37.290 [main] DEBUG org.apache.http.impl.execchain.MainClientExec - Executing request GET / HTTP/1.1
21:52:37.290 [main] DEBUG org.apache.http.impl.execchain.MainClientExec - Target auth state: UNCHALLENGED
21:52:37.290 [main] DEBUG org.apache.http.impl.execchain.MainClientExec - Proxy auth state: UNCHALLENGED
21:52:37.292 [main] DEBUG org.apache.http.headers - http-outgoing-0 >> GET / HTTP/1.1
21:52:37.292 [main] DEBUG org.apache.http.headers - http-outgoing-0 >> Host: localhost:8801
21:52:37.293 [main] DEBUG org.apache.http.headers - http-outgoing-0 >> Connection: Keep-Alive
21:52:37.293 [main] DEBUG org.apache.http.headers - http-outgoing-0 >> User-Agent: Apache-HttpClient/4.5.13 (Java/1.8.0_231)
21:52:37.293 [main] DEBUG org.apache.http.headers - http-outgoing-0 >> Accept-Encoding: gzip,deflate
21:52:37.293 [main] DEBUG org.apache.http.wire - http-outgoing-0 >> "GET / HTTP/1.1[\r][\n]"
21:52:37.293 [main] DEBUG org.apache.http.wire - http-outgoing-0 >> "Host: localhost:8801[\r][\n]"
21:52:37.293 [main] DEBUG org.apache.http.wire - http-outgoing-0 >> "Connection: Keep-Alive[\r][\n]"
21:52:37.293 [main] DEBUG org.apache.http.wire - http-outgoing-0 >> "User-Agent: Apache-HttpClient/4.5.13 (Java/1.8.0_231)[\r][\n]"
21:52:37.293 [main] DEBUG org.apache.http.wire - http-outgoing-0 >> "Accept-Encoding: gzip,deflate[\r][\n]"
21:52:37.293 [main] DEBUG org.apache.http.wire - http-outgoing-0 >> "[\r][\n]"
21:52:37.294 [main] DEBUG org.apache.http.wire - http-outgoing-0 << "HTTP/1.1 200 OK[\n]"
21:52:37.294 [main] DEBUG org.apache.http.wire - http-outgoing-0 << "Content-Type:text/html;charset=utf-8[\n]"
21:52:37.294 [main] DEBUG org.apache.http.wire - http-outgoing-0 << "Content-Length:10[\n]"
21:52:37.294 [main] DEBUG org.apache.http.wire - http-outgoing-0 << "[\n]"
21:52:37.294 [main] DEBUG org.apache.http.wire - http-outgoing-0 << "hello,nio1"
21:52:37.296 [main] DEBUG org.apache.http.headers - http-outgoing-0 << HTTP/1.1 200 OK
21:52:37.297 [main] DEBUG org.apache.http.headers - http-outgoing-0 << Content-Type:text/html;charset=utf-8
21:52:37.297 [main] DEBUG org.apache.http.headers - http-outgoing-0 << Content-Length:10
21:52:37.301 [main] DEBUG org.apache.http.impl.execchain.MainClientExec - Connection can be kept alive indefinitely
Status: HTTP/1.1 200 OK //状态打印输出
21:52:37.306 [main] DEBUG org.apache.http.impl.conn.PoolingHttpClientConnectionManager - Connection [id: 0][route: {}->http://localhost:8801] can be kept alive indefinitely
21:52:37.307 [main] DEBUG org.apache.http.impl.conn.DefaultManagedHttpClientConnection - http-outgoing-0: set socket timeout to 0
21:52:37.307 [main] DEBUG org.apache.http.impl.conn.PoolingHttpClientConnectionManager - Connection released: [id: 0][route: {}->http://localhost:8801][total available: 1; route allocated: 1 of 2; total allocated: 1 of 20]
Result: hello,nio1 //结果打印输出
```

