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

Hello.java代码

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

字节码分析

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



### 作业3



### 作业4



### 作业5

