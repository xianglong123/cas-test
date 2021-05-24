package com.cas.optional;

import com.cas.jol.User;
import org.junit.Test;

import java.util.Optional;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午3:11 2021/4/27
 * @version: V1.0
 * @review:
 */
public class OptionalTest {

    @Test
    public void test() {
        User user = new User();
        Optional<User> user1 = Optional.of(user);
        Boolean aBoolean = user1.map(User::isFlag).orElse(true);
        String name = user1.map(User::getName).orElse("xianglong");
        String val = user1.map(User::getVal).orElse("xianglong");
        System.out.println(aBoolean);
        System.out.println(name);
        System.out.println(val);
    }



}
