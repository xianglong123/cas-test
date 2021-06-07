package com.cas.common;

import com.cas.jol.User;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class MapToBean {

    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
        T obj = beanClass.newInstance();
        for (String field : map.keySet()) {
            Field classField = beanClass.getDeclaredField(field);
            classField.setAccessible(true);
            if (Modifier.isStatic(classField.getModifiers()) || Modifier.isFinal(classField.getModifiers())) {
                continue;
            }
            classField.set(obj, map.get(field));
        }
        return obj;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("flag", true);
        map.put("name", "ww");
        map.put("val", "123");
        User user = mapToObject(map, User.class);
        System.out.println(user);

    }


}
