package com.cas.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/3/29 10:23 上午
 * @desc
 */
public final class StringUtil {

    private static final Map<String, String> map = new HashMap<>();

    public static void put() {
        map.put("name", "xianglong");
    }

    public static String get() {
        return map.get("name");
    }

}
