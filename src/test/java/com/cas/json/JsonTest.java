package com.cas.json;

import com.alibaba.fastjson.JSONObject;
import com.cas.po.W;
import org.junit.Test;

import java.util.List;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午6:12 2021/5/19
 * @version: V1.0
 * @review:
 */
public class JsonTest {

    @Test
    public void test() {
        String str = "{\n" +
                "    \"code\": \"BASE.0000\",\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"month\": \"202101\",\n" +
                "            \"total\": 30\n" +
                "        },\n" +
                "        {\n" +
                "            \"month\": \"202102\",\n" +
                "            \"total\": 12\n" +
                "        },\n" +
                "        {\n" +
                "            \"month\": \"202103\",\n" +
                "            \"total\": 23\n" +
                "        }\n" +
                "    ],\n" +
                "    \"errMsg\": \"\"\n" +
                "}";
        W w = JSONObject.parseObject(str, W.class);
        System.out.println(w);

    }


}
