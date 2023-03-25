package com.cas.io.fileIO.I;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/**
 * author xianglong
 * 该流用于从文件读取谁，他的对象可以用关键字New来创建
 * 有多种构造方法可用来创建对象
 * 可以使用字符串类型的文件名来创建一个可输入流对象的读取文件
 */
public class FileInputStreamTest {

    private static final String path = "/Users/xianglong/IdeaProjects/cas-agwBodyDec/src/agwBodyDec/java/com/cas/io/fileIO/I/hello.txt";

    public static void main(String[] args) {
//            test1();
        test2();
    }

    /**
     * 文件流简单使用 read()
     *
     * @throws Exception
     */
    private static void test1() {
        // 创建文件输入流
        try (FileInputStream fis = new FileInputStream(path);) {
            StringBuilder stb = new StringBuilder();
            int n;
            while ((n = fis.read()) != -1) {
                // 读取字节，但是字节是8位二进制，所以这里转成十进制
                // 这里是通过ASCII码转换获取字符
                //h 104, e 101, ....
                stb.append((char) n);
            }
            System.out.println(stb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 采用read(byte[] a)方式读取字符串
     */
    private static void test2() {
        File file = new File(path);
        // 自动关闭流
        try (FileInputStream fis = new FileInputStream(file);) {
            //从代码形式看，使用read(byte[] b)较为直观和简便，因此项目中可以此方法为主进行数据读取
            int n = 0;
            // 通过文件长度指定字节长度
            byte[] b = new byte[(int) file.length()];
            int i = 0;
            while (n != -1) {
                // 这里的read(byte []) 会读取指定长度的字节，并赋值给输入的字节数组，并返回读取长度
                n = fis.read(b);
                System.out.println("读取数据长度： " + n);
                System.out.println("数组：" + Arrays.toString(b));
                i++;
                System.out.println("执行次数: " + i);
            }
            System.out.println("最终结果： " + new String(b));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
