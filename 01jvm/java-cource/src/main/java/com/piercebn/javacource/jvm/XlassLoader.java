package com.piercebn.javacource.jvm;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class XlassLoader extends  ClassLoader {
    public static void main(String[] args) {
        try {
            // 类名和方法定义
            String className = "Hello";
            String methodName = "hello";
            // 创建自定义的类加载器，用来加载已xlass结尾的类文件
            ClassLoader classLoader = new XlassLoader();
            // 加载相应的类
            Class<?> clazz = classLoader.loadClass(className);
            // 打印类的所有方法
            for (Method m : clazz.getDeclaredMethods()) {
                System.out.println(clazz.getSimpleName() + "." + m.getName());
            }
            // 创建对象实例
            Object instance = clazz.getDeclaredConstructor().newInstance();
            // 获取并调用实例方法
            Method method = clazz.getMethod(methodName);
            method.invoke(instance);
        } catch (ClassNotFoundException | NoSuchMethodException e ) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 根据类名构建类文件相对路径
        String classFilePath = name+".xlass";
        // 获取输入流
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(classFilePath);
        if(inputStream == null) {
            throw new ClassNotFoundException(classFilePath+" is not exist!");
        }
        try {
            // 读取xlass文件数据
            int length = inputStream.available();
            byte[] byteArray = new byte[length];
            inputStream.read(byteArray);
            // xlass -> class
            byte[] classBytes = decode(byteArray);
            // 使用转换后的byte数组定义类
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(classFilePath+" decode fail!", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // xlass解码
    private byte[] decode(byte[] byteArray) {
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) (255 - byteArray[i]); // 或 (byte) (~byteArray[i]);
        }
        return byteArray;
    }
}
