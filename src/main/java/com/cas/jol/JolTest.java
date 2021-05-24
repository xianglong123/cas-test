package com.cas.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午7:58 2021/4/26
 * @version: V1.0
 * @review: 测试查看对象头包含信息
 */
public class JolTest {

    public static void main(String[] args) {

        User user = new User();
//        user.hashCode();
//        System.out.println(user.hashCode());
//        System.out.println(Integer.toHexString(user.hashCode()));
        System.out.println(ClassLayout.parseInstance(user).toPrintable());

//        int[] array = new int[3];
//        array[0] = 11;
//        array[1] = 22;
//        array[2] = 33;
//        System.out.println( ClassLayout.parseInstance(array).toPrintable() );
    }

}
