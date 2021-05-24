package com.cas.rsa;

import com.cas.jol.User;
import org.junit.Test;

import java.security.KeyStore;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午9:29 2021/5/14
 * @version: V1.0
 * @review:
 */
public class RSAUtils {


    @Test
    public void test1() {

    }


    @Test
    public void test() {
        List<User> list = new ArrayList<>();
        list.add(new User(false, "xl", "123"));
        list.add(new User(false, "xl", "123"));
        list.add(new User(false, "xl", "1235"));
        list.add(new User(false, "xl2", "1234"));
        list.add(new User(true, "xl2", "1234"));

        List<User> collect = list.stream().distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test2() {
        Set<String> set = new HashSet<>();
        System.out.println(set.add("a"));
        System.out.println(set.add("a"));
    }

}
