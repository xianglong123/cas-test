package com.cas.clazz;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/11/16 5:21 下午
 * @desc
 */
public class ClazzTest {

    @Test
    public void test() throws Exception{
        String beanPath = "com.cas.clazz.Person";
        Class clazz = Class.forName(beanPath);
        Constructor constructor = clazz.getConstructor(String.class, Integer.class, Integer.class, boolean.class);
        Person instance = (Person) constructor.newInstance("xl", 12, 189, true);
        System.out.println(instance.getClass());
        System.out.println(JSONObject.toJSONString(instance));
    }

    @Test
    public void test2() throws Exception {
        String beanPath = "com.cas.clazz.Person";
        Class clazz = Class.forName(beanPath);
        Constructor constructor = clazz.getConstructor();
        Object instance = constructor.newInstance();
        Method method = clazz.getMethod("like", String.class);
        Object xl = method.invoke(instance,"xl");
        System.out.println(xl);
    }


    @Test
    public void test3() throws Exception {
        String beanPath = "com.cas.clazz.Person";
        Class clazz = Class.forName(beanPath);
        Constructor constructor = clazz.getConstructor(String.class, Integer.class, Integer.class, boolean.class);
        Object instance = constructor.newInstance("xl", 12, 189, true);
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        Object name = field.get(instance);
        System.out.println(name);
    }

}
